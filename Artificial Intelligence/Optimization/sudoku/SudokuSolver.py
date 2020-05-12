import copy
import sudoku


class SudoSolver():
    INFINITY = 10

    def __init__(self, input_sudo):
        self.sudo = input_sudo

    def solve(self):
        """
        Enforce node and arc consistency, and then solve the CSP.
        """
        print("Input Sudoku: ")
        self.sudo.print()
        print("-" * self.sudo.size * 4)
        print("\nOutput Sudoku: ")
        self.ac3()
        if self.backtrack():
            self.sudo.print()
        else:
            print("Sorry, couldn't find any Solution\nPlease check if the input is parsed correctly")

    def revise(self, x, y):
        """
        Make variable `x` arc consistent with variable `y`.
        Return True if a revision was made to the domain of `x`; return
        False if no revision was made.
        """
        # Assuming that revise will only be called upon those X and Y who are neighbors
        revised = False
        to_remove = []
        for x_value in self.sudo.domains[x]:
            found_any = False
            if y in self.sudo.domains:
                for y_value in self.sudo.domains[y]:
                    if x_value != y_value:
                        found_any = True
                        break
                if not found_any:
                    to_remove.append(x_value)
                    revised = True
            else:
                if x_value == y.value:
                    to_remove.append(x_value)
                    revised = True
        for x_value in to_remove:
            self.sudo.domains[x].remove(x_value)
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
            for x_variable in self.sudo.variables:
                for y_variable in self.sudo.neighbors[x_variable]:
                    arcs.append((x_variable, y_variable))

        while len(arcs) > 0:
            arc = arcs.pop(0)
            x_variable = arc[0]
            y_variable = arc[1]
            if self.revise(x_variable, y_variable):
                if len(self.sudo.domains[x_variable]) == 0:
                    return False
                for neighbor in self.sudo.neighbors[x_variable]:
                    if neighbor in self.sudo.domains:
                        arcs.append((neighbor, x_variable))
        return True

    def assignment_complete(self):
        """
        Return True if `assignment` is complete (i.e., assigns a value to each
        crossword variable); return False otherwise.
        """
        for variable in self.sudo.variables:
            if variable.value is None:
                return False
        return True

    def consistent(self, var):
        """
        Return True if `assignment` is consistent (i.e., values fit in sudoku
        puzzle without conflicting neighbors); return False otherwise.
        """
        variable_value = var.value
        for neighbor in self.sudo.neighbors[var]:
            neighbor_value = neighbor.value
            if neighbor_value is not None:
                if variable_value == neighbor_value:
                    return False
        return True

    def order_domain_values(self, var):
        """
        Return a list of values in the domain of `var`, in order by
        the number of values they rule out for neighboring variables.
        The first value in the list, for example, should be the one
        that rules out the fewest values among the neighbors of `var`.
        """
        return self.sudo.domains[var]

    def select_unassigned_variable(self):
        """
        Return an unassigned variable not already part of `assignment`.
        Choose the variable with the minimum number of remaining values
        in its domain. If there is a tie, choose the variable with the highest
        degree. If there is a tie, any of the tied variables are acceptable
        return values.
        """
        store = []
        # Domain of all variables is less than 10
        store_length = SudoSolver.INFINITY
        for variable in self.sudo.variables:
            if variable.value is None:
                variable_length = len(self.sudo.domains[variable])
                if variable_length < store_length:
                    store = [variable]
                    store_length = variable_length
                elif variable_length == store_length:
                    store.append(variable)

        if len(store) == 1:
            return store[0]
        else:
            out = None
            out_degree = -1
            for variable in store:
                number_of_neighbors = 0
                for neighbor in self.sudo.neighbors[variable]:
                    if neighbor in self.sudo.domains:
                        number_of_neighbors += 1
                if number_of_neighbors > out_degree:
                    out = variable
                    out_degree = number_of_neighbors
            return out

    def backtrack(self):
        """
        Using Backtracking Search, take as input a partial assignment for the
        crossword and return a complete assignment if possible to do so.

        `assignment` is a mapping from variables (keys) to words (values).

        If no assignment is possible, return None.
        """
        if self.assignment_complete():
            return True
        unassigned_variable = self.select_unassigned_variable()
        for value in self.order_domain_values(unassigned_variable):
            unassigned_variable.value = value
            # print(str(unassigned_variable.location) + ": " + str(value))
            # self.sudo.print()
            # print("---------------------------------------")
            new_domain = None
            if self.consistent(unassigned_variable):
                new_domain = self.copy_domain()
                self.sudo.domains[unassigned_variable] = {value}
                input_for_ac3 = []
                for neighbor in self.sudo.neighbors[unassigned_variable]:
                    if neighbor in self.sudo.domains:
                        input_for_ac3.append((neighbor, unassigned_variable))

                if self.ac3(input_for_ac3):
                    backtrack_result = self.backtrack()
                    if backtrack_result:
                        return backtrack_result
            unassigned_variable.value = None
            if new_domain is not None:
                self.sudo.domains = new_domain
        return False

    def copy_domain(self):
        out = dict()
        for variable in self.sudo.domains:
            out[variable] = copy.deepcopy(self.sudo.domains[variable])
        return out


def main():
    obj = SudoSolver(sudoku.Sudoku("data/structure0.txt"))
    obj.solve()


if __name__ == "__main__":
    main()
    input("Press Enter to exit")
