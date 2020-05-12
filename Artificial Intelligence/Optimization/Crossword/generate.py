import sys
import copy

from crossword import *


class CrosswordCreator():

    def __init__(self, crossword):
        """
        Create new CSP crossword generate.
        """
        self.crossword = crossword
        self.domains = {
            var: self.crossword.words.copy()
            for var in self.crossword.variables
        }

    def letter_grid(self, assignment):
        """
        Return 2D array representing a given assignment.
        """
        letters = [
            [None for _ in range(self.crossword.width)]
            for _ in range(self.crossword.height)
        ]
        for variable, word in assignment.items():
            direction = variable.direction
            for k in range(len(word)):
                i = variable.i + (k if direction == Variable.DOWN else 0)
                j = variable.j + (k if direction == Variable.ACROSS else 0)
                letters[i][j] = word[k]
        return letters

    def print(self, assignment):
        """
        Print crossword assignment to the terminal.
        """
        letters = self.letter_grid(assignment)
        for i in range(self.crossword.height):
            for j in range(self.crossword.width):
                if self.crossword.structure[i][j]:
                    print(letters[i][j] or " ", end="")
                else:
                    print("█", end="")
            print()

    def save(self, assignment, filename):
        """
        Save crossword assignment to an image file.
        """
        from PIL import Image, ImageDraw, ImageFont
        cell_size = 100
        cell_border = 2
        interior_size = cell_size - 2 * cell_border
        letters = self.letter_grid(assignment)

        # Create a blank canvas
        img = Image.new(
            "RGBA",
            (self.crossword.width * cell_size,
             self.crossword.height * cell_size),
            "black"
        )
        font = ImageFont.truetype("assets/fonts/OpenSans-Regular.ttf", 80)
        draw = ImageDraw.Draw(img)

        for i in range(self.crossword.height):
            for j in range(self.crossword.width):

                rect = [
                    (j * cell_size + cell_border,
                     i * cell_size + cell_border),
                    ((j + 1) * cell_size - cell_border,
                     (i + 1) * cell_size - cell_border)
                ]
                if self.crossword.structure[i][j]:
                    draw.rectangle(rect, fill="white")
                    if letters[i][j]:
                        w, h = draw.textsize(letters[i][j], font=font)
                        draw.text(
                            (rect[0][0] + ((interior_size - w) / 2),
                             rect[0][1] + ((interior_size - h) / 2) - 10),
                            letters[i][j], fill="black", font=font
                        )

        img.save(filename)

    def solve(self):
        """
        Enforce node and arc consistency, and then solve the CSP.
        """
        self.enforce_node_consistency()
        self.ac3()
        return self.backtrack(dict())

    def enforce_node_consistency(self):
        """
        Update `self.domains` such that each variable is node-consistent.
        (Remove any values that are inconsistent with a variable's unary
         constraints; in this case, the length of the word.)
        """
        to_remove = []
        for variable in self.domains:
            for word in self.domains[variable]:
                if len(word) != variable.length:
                    to_remove.append(word)
            while len(to_remove) > 0:
                self.domains[variable].remove(to_remove.pop())

    def revise(self, x, y):
        """
        Make variable `x` arc consistent with variable `y`.
        To do so, remove values from `self.domains[x]` for which there is no
        possible corresponding value for `y` in `self.domains[y]`.

        Return True if a revision was made to the domain of `x`; return
        False if no revision was made.
        """
        # Assuming that revise will only be called upon those X and Y who are overlapping
        # Try to understand why I used x_word != y_word (in line 125)
        revised = False
        match = self.crossword.overlaps[x, y]
        to_remove = []
        for x_word in self.domains[x]:
            found_any = False
            i_char = x_word[match[0]]
            for y_word in self.domains[y]:
                j_char = y_word[match[1]]
                if i_char == j_char and x_word != y_word:
                    found_any = True
                    break
            if not found_any:
                to_remove.append(x_word)
                revised = True
        for x_word in to_remove:
            self.domains[x].remove(x_word)
        return revised

    def ac3(self, arcs=None):
        """
        Update `self.domains` such that each variable is arc consistent.
        If `arcs` is None, begin with initial list of all arcs in the problem.
        Otherwise, use `arcs` as the initial list of arcs to make consistent.

        Return True if arc consistency is enforced and no domains are empty;
        return False if one or more domains end up empty.
        """
        # Revise should be called both ways
        # Haven't excluded y_variable in line 156 because it shouldn't be
        if arcs is None:
            arcs = []
            for x_variable in self.crossword.variables:
                for y_variable in self.crossword.neighbors(x_variable):
                    arcs.append((x_variable, y_variable))

        while len(arcs) > 0:
            arc = arcs.pop(0)
            x_variable = arc[0]
            y_variable = arc[1]
            if self.revise(x_variable, y_variable):
                if len(self.domains[x_variable]) == 0:
                    return False
                for neighbor in self.crossword.neighbors(x_variable):
                    arcs.append((neighbor, x_variable))
        return True

    def assignment_complete(self, assignment):
        """
        Return True if `assignment` is complete (i.e., assigns a value to each
        crossword variable); return False otherwise.
        """
        if len(assignment) == len(self.crossword.variables):
            return True
        return False

    def consistent(self, assignment):
        """
        Return True if `assignment` is consistent (i.e., words fit in crossword
        puzzle without conflicting characters); return False otherwise.
        """
        words_used = set()
        checked = set()
        for assigned_variable in assignment:
            word = assignment[assigned_variable]
            if word in words_used or len(word) != assigned_variable.length:
                return False
            for neighbor in self.crossword.neighbors(assigned_variable):
                if neighbor not in checked and neighbor in assignment:
                    match = self.crossword.overlaps[assigned_variable, neighbor]
                    neighbors_word = assignment[neighbor]
                    try:
                        if word[match[0]] != neighbors_word[match[1]]:
                            return False
                    except IndexError:
                        return False
            checked.add(assigned_variable)
            words_used.add(word)
        return True

    def order_domain_values(self, var, assignment):
        """
        Return a list of values in the domain of `var`, in order by
        the number of values they rule out for neighboring variables.
        The first value in the list, for example, should be the one
        that rules out the fewest values among the neighbors of `var`.
        """
        out_arr = []
        ruled_out_arr = []
        neighbors = self.crossword.neighbors(var)
        for var_value in self.domains[var]:
            number_ruled_out = 0
            for neighbor in neighbors:
                if neighbor not in assignment:
                    match = self.crossword.overlaps[var, neighbor]
                    for neighbor_value in self.domains[neighbor]:
                        try:
                            if var_value[match[0]] != neighbor_value[match[1]] or var_value == neighbor_value:
                                number_ruled_out += 1
                        except IndexError:
                            number_ruled_out += 1

            place_at = len(out_arr)
            for i in range(len(out_arr)):
                if number_ruled_out <= ruled_out_arr[i]:
                    place_at = i
                    break
            out_arr.insert(place_at, var_value)
            ruled_out_arr.insert(place_at, number_ruled_out)
        return out_arr

    def select_unassigned_variable(self, assignment):
        """
        Return an unassigned variable not already part of `assignment`.
        Choose the variable with the minimum number of remaining values
        in its domain. If there is a tie, choose the variable with the highest
        degree. If there is a tie, any of the tied variables are acceptable
        return values.
        """
        store = None
        for variable in self.crossword.variables:
            if variable not in assignment:
                if store is None:
                    store = [variable]
                else:
                    if len(self.domains[variable]) < len(self.domains[store[0]]):
                        store = [variable]
                    elif len(self.domains[variable]) == len(self.domains[store[0]]):
                        store.append(variable)

        if len(store) == 1:
            return store[0]
        else:
            out = store[0]
            out_degree = len(self.crossword.neighbors(store[0]))
            for variable in store:
                if len(self.crossword.neighbors(variable)) > out_degree:
                    out = variable
                    out_degree = len(self.crossword.neighbors(variable))
            return out

    def backtrack(self, assignment):
        """
        Using Backtracking Search, take as input a partial assignment for the
        crossword and return a complete assignment if possible to do so.

        `assignment` is a mapping from variables (keys) to words (values).

        If no assignment is possible, return None.
        """
        if self.assignment_complete(assignment):
            return assignment
        unassigned_variable = self.select_unassigned_variable(assignment)
        for value in self.order_domain_values(unassigned_variable, assignment):
            assignment[unassigned_variable] = value
            new_domain = None
            if self.consistent(assignment):
                new_domain = self.copy_domain()
                self.domains[unassigned_variable] = {value}
                input_for_ac3 = []
                for neighbor in self.crossword.neighbors(unassigned_variable):
                    input_for_ac3.append((neighbor, unassigned_variable))

                if self.ac3(input_for_ac3):
                    backtrack_result = self.backtrack(assignment)
                    if backtrack_result is not None:
                        return backtrack_result
            del assignment[unassigned_variable]
            if new_domain is not None:
                self.domains = new_domain
        return None

    def copy_domain(self):
        out = dict()
        for variable in self.domains:
            out[variable] = copy.deepcopy(self.domains[variable])
        return out


def main():

    # Check usage
    if len(sys.argv) not in [3, 4]:
        sys.exit("Usage: python generate.py structure words [output]")

    # Parse command-line arguments
    structure = sys.argv[1]
    words = sys.argv[2]
    output = sys.argv[3] if len(sys.argv) == 4 else None

    # Generate crossword
    crossword = Crossword(structure, words)
    creator = CrosswordCreator(crossword)
    assignment = creator.solve()

    # Print result
    if assignment is None:
        print("No solution.")
    else:
        creator.print(assignment)
        if output:
            creator.save(assignment, output)


if __name__ == "__main__":
    main()
