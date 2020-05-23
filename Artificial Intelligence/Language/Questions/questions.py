import nltk
import sys
import os
import string
import math

FILE_MATCHES = 1
SENTENCE_MATCHES = 1


def main():
    # Check command-line arguments
    if len(sys.argv) != 2:
        sys.exit("Usage: python questions.py corpus")

    # Calculate IDF values across files
    files = load_files(sys.argv[1])
    file_words = {
        filename: tokenize(files[filename])
        for filename in files
    }
    file_idfs = compute_idfs(file_words)

    # Prompt user for query
    query = set(tokenize(input("Query: ")))

    # Determine top file matches according to TF-IDF
    filenames = top_files(query, file_words, file_idfs, n=FILE_MATCHES)

    # Extract sentences from top files
    sentences = dict()
    for filename in filenames:
        for passage in files[filename].split("\n"):
            for sentence in nltk.sent_tokenize(passage):
                tokens = tokenize(sentence)
                if tokens:
                    sentences[sentence] = tokens

    # Compute IDF values across sentences
    idfs = compute_idfs(sentences)

    # Determine top sentence matches
    matches = top_sentences(query, sentences, idfs, n=SENTENCE_MATCHES)
    for match in matches:
        print(match)


def load_files(directory):
    """
    Given a directory name, return a dictionary mapping the filename of each
    `.txt` file inside that directory to the file's contents as a string.
    """
    out = dict()
    for file in os.listdir(directory):
        print("read file: " + file)
        with open(os.path.join(directory, file), 'r', encoding='utf-8') as f:
            out[file] = f.read()
    return out


def tokenize(document):
    """
    Given a document (represented as a string), return a list of all of the
    words in that document, in order.

    Process document by converting all words to lowercase, and removing any
    punctuation or English stopwords.
    """
    temp_out = nltk.tokenize.word_tokenize(document)
    stop_words = nltk.corpus.stopwords.words("english")
    punctuations = string.punctuation
    out = []
    for i in range(len(temp_out)):
        word = temp_out[i]
        if word in stop_words or word in punctuations:
            continue
        else:
            new_word = ''
            for character in word:
                if 65 <= ord(character) <= 90:
                    new_word += chr(ord(character) + 32)
                else:
                    new_word += character
            if new_word and new_word not in stop_words:
                out.append(new_word)
    return out


def compute_idfs(documents):
    """
    Given a dictionary of `documents` that maps names of documents to a list
    of words, return a dictionary that maps words to their IDF values.

    Any word that appears in at least one of the documents should be in the
    resulting dictionary.
    """
    out = dict()
    total_documents = len(documents)
    for document in documents:
        contents = set(documents[document])
        for word in contents:
            if word in out:
                out[word] += 1
            else:
                out[word] = 1

    for word in out:
        out[word] = math.log(total_documents / out[word])
    return out


def top_files(query, files, idfs, n):
    """
    Given a `query` (a set of words), `files` (a dictionary mapping names of
    files to a list of their words), and `idfs` (a dictionary mapping words
    to their IDF values), return a list of the filenames of the the `n` top
    files that match the query, ranked according to tf-idf.
    """
    out = []
    scores = []
    for file in files:
        file_score = 0
        contents = files[file]
        for word in query:
            frequency = 0
            for content in contents:
                if content == word:
                    frequency += 1
            if frequency > 0:
                file_score += frequency * idfs[word]

        insert_at = len(scores)
        for score in scores:
            if file_score >= score:
                insert_at = scores.index(score)
                break
        out.insert(insert_at, file)
        scores.insert(insert_at, file_score)
    return out[:n]


def top_sentences(query, sentences, idfs, n):
    """
    Given a `query` (a set of words), `sentences` (a dictionary mapping
    sentences to a list of their words), and `idfs` (a dictionary mapping words
    to their IDF values), return a list of the `n` top sentences that match
    the query, ranked according to idf. If there are ties, preference should
    be given to sentences that have a higher query term density.
    """
    # out = dict()
    # for sentence in sentences:
    #    out[sentence] = 0
    '''
    word_dict = dict()
    for word in query:
        word_dict[word] = 0
        for sentence in sentences:
            contents = sentences[sentence]
            if word in contents:
                word_dict[word] += 1
        if word_dict[word] > 0:
            word_dict[word] = math.log(len(sentences) / word_dict[word])
    '''

    out_sentence = []
    out_score = []
    for sentence in sentences:
        sentence_score = 0
        contents = sentences[sentence]
        for word in query:
            if word in contents:
                sentence_score += idfs[word]

        """ Placing the sentence in the out_sentence such that out_sentence maintains it ascending order """
        insert_at = len(out_sentence)
        for i in range(len(out_score)):
            score = out_score[i]
            if sentence_score > score:
                insert_at = i
                break
            elif sentence_score == score:
                if compare_query_term_density(query, sentences, sentence, out_sentence[i]):
                    insert_at = i
                    break
        out_sentence.insert(insert_at, sentence)
        out_score.insert(insert_at, sentence_score)
    return out_sentence[:n]


def compare_query_term_density(query, sentences, base_sentence, compare_sentence):
    """
    Compares base_sentence with compare_sentence (with equal idf scores) on the basis of Query Term Density
    Returns True if base_sentence has greater or equal value of Query Term Density to compare_sentence
    """
    compare_input = dict()
    compare_input[base_sentence] = 0
    compare_input[compare_sentence] = 0
    for sentence in compare_input:
        contents = sentences[sentence]
        for word in contents:
            if word in query:
                compare_input[sentence] += 1
        compare_input[sentence] = compare_input[sentence] / len(contents)
    if compare_input[base_sentence] >= compare_input[compare_sentence]:
        return True
    else:
        return False


if __name__ == "__main__":
    main()
