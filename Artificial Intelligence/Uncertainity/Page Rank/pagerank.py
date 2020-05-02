import os
import random
import re
import sys
import numpy

DAMPING = 0.85
SAMPLES = 10000


def main():
    if len(sys.argv) != 2:
        sys.exit("Usage: python pagerank.py corpus")
    corpus = crawl(sys.argv[1])
    ranks = sample_pagerank(corpus, DAMPING, SAMPLES)
    print(f"PageRank Results from Sampling (n = {SAMPLES})")
    for page in sorted(ranks):
        print(f"  {page}: {ranks[page]:.4f}")
    ranks = iterate_pagerank(corpus, DAMPING)
    print(f"PageRank Results from Iteration")
    for page in sorted(ranks):
        print(f"  {page}: {ranks[page]:.4f}")


def crawl(directory):
    """
    Parse a directory of HTML pages and check for links to other pages.
    Return a dictionary where each key is a page, and values are
    a list of all other pages in the corpus that are linked to by the page.
    """
    pages = dict()

    # Extract all links from HTML files
    for filename in os.listdir(directory):
        if not filename.endswith(".html"):
            continue
        with open(os.path.join(directory, filename)) as f:
            contents = f.read()
            links = re.findall(r"<a\s+(?:[^>]*?)href=\"([^\"]*)\"", contents)
            pages[filename] = set(links) - {filename}

    # Only include links to other pages in the corpus
    for filename in pages:
        pages[filename] = set(
            link for link in pages[filename]
            if link in pages
        )

    return pages


def transition_model(corpus, page, damping_factor):
    """
    Return a probability distribution over which page to visit next,
    given a current page.

    With probability `damping_factor`, choose a link at random
    linked to by `page`. With probability `1 - damping_factor`, choose
    a link at random chosen from all pages in the corpus.
    """
    # Creating an initial output dictionary with all keys probability as 0
    out = {}
    for key in corpus:
        out[key] = 0

    links = corpus[page]
    length_links = 0
    for link in links:
        if link != page:
            length_links = length_links + 1
    if length_links == 0:
        damping_factor = 0
    else:
        for link in links:
            if link != page:
                out[link] = damping_factor * (1 / length_links)

    for page in corpus:
        out[page] = out[page] + ((1 - damping_factor) * (1 / len(corpus)))

    return out


def sample_pagerank(corpus, damping_factor, n):
    """
    Return PageRank values for each page by sampling `n` pages
    according to transition model, starting with a page at random.

    Return a dictionary where keys are page names, and values are
    their estimated PageRank value (a value between 0 and 1). All
    PageRank values should sum to 1.
    """
    out = {}
    for key in corpus:
        out[key] = 0

    sample = list(numpy.random.choice(list(corpus.keys()), 1, False))

    while n > 1:
        n = n - 1
        last_added = sample[-1]
        transition = transition_model(corpus, last_added, damping_factor)
        randomly_generated = numpy.random.choice(list(transition.keys()), 1, False, list(transition.values()))
        sample.append(randomly_generated[0])

    for page in sample:
        out[page] = out[page] + 1

    for page in out:
        out[page] = (out[page] / len(sample))

    return out


def iterate_pagerank(corpus, damping_factor):
    """
    Return PageRank values for each page by iteratively updating
    PageRank values until convergence.

    Return a dictionary where keys are page names, and values are
    their estimated PageRank value (a value between 0 and 1). All
    PageRank values should sum to 1.
    """
    out = {}
    for key in corpus:
        out[key] = (1 / len(corpus))

    new_out = {}

    while True:
        for key1 in out:
            temp = 0
            for key2 in out:
                if key1 in corpus[key2]:
                    temp = temp + (page_rank(out, key2) / num_links(corpus, key2))
            new_out[key1] = ((1 - damping_factor) / len(corpus)) + (damping_factor * temp)

        if do_stop(out, new_out):
            out = new_out
            break
        else:
            temp = out
            out = new_out
            new_out = temp
    return out


def num_links(corpus, page):
    links = corpus[page]
    numlinks = 0
    for link in links:
        if link != page:
            numlinks = numlinks + 1
    return numlinks


def page_rank(out, page):
    return out[page]


def do_stop(out1, out2):
    for key in out1:
        difference = out1[key] - out2[key]
        if -0.001 < difference > 0.001:
            return False
    return True


if __name__ == "__main__":
    main()
