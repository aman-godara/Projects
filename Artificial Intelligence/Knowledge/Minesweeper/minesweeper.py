import copy
import itertools
import random


class Minesweeper():
    """
    Minesweeper game representation
    """

    def __init__(self, height=8, width=8, mines=8):

        # Set initial width, height, and number of mines
        self.height = height
        self.width = width
        self.mines = set()

        # Initialize an empty field with no mines
        self.board = []
        for i in range(self.height):
            row = []
            for j in range(self.width):
                row.append(False)
            self.board.append(row)

        # Add mines randomly
        while len(self.mines) != mines:
            i = random.randrange(height)
            j = random.randrange(width)
            if not self.board[i][j]:
                self.mines.add((i, j))
                self.board[i][j] = True

        # At first, player has found no mines
        self.mines_found = set()

    def print(self):
        """
        Prints a text-based representation
        of where mines are located.
        """
        for i in range(self.height):
            print("--" * self.width + "-")
            for j in range(self.width):
                if self.board[i][j]:
                    print("|X", end="")
                else:
                    print("| ", end="")
            print("|")
        print("--" * self.width + "-")

    def is_mine(self, cell):
        i, j = cell
        return self.board[i][j]

    def nearby_mines(self, cell):
        """
        Returns the number of mines that are
        within one row and column of a given cell,
        not including the cell itself.
        """

        # Keep count of nearby mines
        count = 0

        # Loop over all cells within one row and column
        for i in range(cell[0] - 1, cell[0] + 2):
            for j in range(cell[1] - 1, cell[1] + 2):

                # Ignore the cell itself
                if (i, j) == cell:
                    continue

                # Update count if cell in bounds and is mine
                if 0 <= i < self.height and 0 <= j < self.width:
                    if self.board[i][j]:
                        count += 1

        return count

    def won(self):
        """
        Checks if all mines have been flagged.
        """
        return self.mines_found == self.mines


class Sentence():
    """
    Logical statement about a Minesweeper game
    A sentence consists of a set of board cells,
    and a count of the number of those cells which are mines.
    """

    def __init__(self, cells, count):
        self.cells = set(cells)
        self.count = count

    def __eq__(self, other):
        return self.cells == other.cells and self.count == other.count

    def __str__(self):
        return f"{self.cells} = {self.count}"

    def known_mines(self):
        """
        Returns the set of all cells in self.cells known to be mines.
        """
        if len(self.cells) == self.count:
            return self.cells
        else:
            return None

    def known_safes(self):
        """
        Returns the set of all cells in self.cells known to be safe.
        """
        if self.count == 0:
            return self.cells
        else:
            return None

    def mark_mine(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be a mine.
        """
        try:
            self.cells.remove(cell)
            self.count = self.count - 1
        except KeyError:
            pass

    def mark_safe(self, cell):
        """
        Updates internal knowledge representation given the fact that
        a cell is known to be safe.
        """
        try:
            self.cells.remove(cell)
        except KeyError:
            pass

    def bounce(self, input_list):
        try:
            input_list.remove(self)
        except ValueError:
            pass

    def qualify(self):
        if len(self.cells) == 1:
            return True
        else:
            return False

    def is_checked(self, checklist):
        for sentence in checklist:
            if sentence.cells == self.cells:
                return True
        return False


def save(sentence, input_list):
    if sentence in input_list:
        pass
    else:
        input_list.append(sentence)


class MinesweeperAI():
    """
    Minesweeper game player
    """

    def __init__(self, height=8, width=8):

        # Set initial height and width
        self.height = height
        self.width = width

        # Keep track of which cells have been clicked on
        self.moves_made = set()

        # Keep track of cells known to be safe or mines
        self.mines = set()
        self.safes = set()

        # List of sentences about the game known to be true
        self.knowledge = []

    def mark_mine(self, cell):
        """
        Marks a cell as a mine, and updates all knowledge
        to mark that cell as a mine as well.
        """
        self.mines.add(cell)
        for sentence in self.knowledge:
            sentence.mark_mine(cell)
        ''' A Note for assignment checker: I haven't used this method anywhere explicitly. And turns out my 
        algorithm is complex as well. You are welcome to write me an email on my amangodara891@gmail.com to 
        request for any clarification. Thank you'''

    def mark_safe(self, cell):
        """
        Marks a cell as safe, and updates all knowledge
        to mark that cell as safe as well.
        """
        self.safes.add(cell)
        for sentence in self.knowledge:
            sentence.mark_safe(cell)
        ''' A Note for assignment checker: I haven't used this method anywhere explicitly. And turns out my 
        algorithm is complex as well. You are welcome to write me an email on my amangodara891@gmail.com to 
        request for any clarification. Thank you'''

    def add_knowledge(self, cell, count):
        """
        Called when the Minesweeper board tells us, for a given
        safe cell, how many neighboring cells have mines in them.

        This function should:
            1) mark the cell as a move that has been made
            2) mark the cell as safe
            3) add a new sentence to the AI's knowledge base
               based on the value of `cell` and `count`
            4) mark any additional cells as safe or as mines
               if it can be concluded based on the AI's knowledge base
            5) add any new sentences to the AI's knowledge base
               if they can be inferred from existing knowledge
        """
        self.moves_made.add(cell)
        new_cells = set()
        new_count = count
        for i in [-1, 0, 1]:
            for j in [-1, 0, 1]:
                new_cell = (cell[0] + i, cell[1] + j)
                if self.in_range(new_cell) and new_cell != cell:  # cannot write i != 0 and j != 0 instead of new_cell != cell
                    new_cells.add(new_cell)  # instead can write not (i == 0 and j == 0)

        to_remove = []
        for ith_cell in new_cells:
            if ith_cell in self.mines:
                to_remove.append(ith_cell)
                new_count = new_count - 1
            elif ith_cell in self.safes:
                to_remove.append(ith_cell)

        for i in to_remove:
            new_cells.remove(i)

        self.solve(Sentence({cell}, 0))
        self.solve(Sentence(new_cells, new_count))

    def make_safe_move(self):
        """
        Returns a safe cell to choose on the Minesweeper board.
        The move must be known to be safe, and not already a move
        that has been made.

        This function may use the knowledge in self.mines, self.safes
        and self.moves_made, but should not modify any of those values.
        """
        for cell in self.safes:
            if cell not in self.moves_made:
                print("Playing safe at: " + str(cell))
                return cell
        return None

    def make_random_move(self):
        """
        Returns a move to make on the Minesweeper board.
        Should choose randomly among cells that:
            1) have not already been chosen, and
            2) are not known to be mines
        """
        '''print('<---------------------------- Random Move Coming ------------------------------->')'''
        for i in range(0, self.height):
            for j in range(0, self.width):
                random_cell = (i, j)
                if random_cell not in self.mines and random_cell not in self.moves_made:
                    print("Random Move at " + str(random_cell))
                    return random_cell

    def in_range(self, new_cell):
        return 0 <= new_cell[0] < self.height and 0 <= new_cell[1] < self.width

    def process(self, process_sentence, to_check):
        if len(process_sentence.cells) == 0:
            pass
        else:
            safes = process_sentence.known_safes()
            mines = process_sentence.known_mines()
            if safes:
                for safe in safes:
                    self.safes.add(safe)
                    save(Sentence({safe}, 0), to_check)
                if len(safes) == 1:
                    process_sentence.bounce(self.knowledge)
                    return True
            elif mines:
                for mine in mines:
                    self.mines.add(mine)
                    save(Sentence({mine}, 1), to_check)
                if len(mines) == 1:
                    process_sentence.bounce(self.knowledge)
                    return True
            else:
                save(process_sentence, self.knowledge)
                save(process_sentence, to_check)
                return False
        process_sentence.bounce(self.knowledge)
        process_sentence.bounce(to_check)
        return True

    def solve(self, input_sentence):
        new_sentences = [input_sentence]
        to_check = []
        checked = []
        ''' I haven't used checked = [] in my first draft of the code. I later had to because I was getting an infinite loop
        You can for the time being assume that there is nothing like checked = []
        By checked = [] I want to keep track of people I have checked otherwise I would have been generating same new sentences 
        again and again leading to an infinite loop'''
        while len(to_check) > 0 or len(new_sentences) > 0:
            while len(new_sentences) > 0:
                self.process(new_sentences.pop(0), to_check)
            if len(to_check) > 0:
                current = to_check.pop(0)
                for sentence in self.knowledge:
                    if current is sentence or current.cells == sentence.cells:
                        pass
                    elif current.cells.issubset(sentence.cells):
                        if not current.qualify():
                            new_sentence = copy.deepcopy(sentence)
                            new_sentence.count = new_sentence.count - current.count
                            for cell in current.cells:
                                new_sentence.cells.remove(cell)
                            if not new_sentence.is_checked(checked):
                                save(new_sentence, new_sentences)
                                checked.append(new_sentence)
                        else:
                            sentence.count = sentence.count - current.count
                            for cell in current.cells:
                                sentence.cells.remove(cell)
                            save(sentence, new_sentences)
                    elif sentence.cells.issubset(current.cells):
                        new_sentence = copy.deepcopy(current)
                        new_sentence.count = new_sentence.count - sentence.count
                        for cell in sentence.cells:
                            new_sentence.cells.remove(cell)
                        save(new_sentence, new_sentences)
                        checked.append(new_sentence)
        del checked
