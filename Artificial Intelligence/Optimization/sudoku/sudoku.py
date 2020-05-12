import math


class Variable():

    def __init__(self, i, j, value=None):
        self.location = (i, j)
        self.value = value


class Sudoku():

    def __init__(self, structure_file, size=9):
        self.size = size
        """ Creating a representation of the Sudoku board in self.board """
        self.board = []
        """ To keep record of all the variables in self.variables """
        self.variables = set()
        file_object = open(structure_file, 'r')
        line = file_object.readline()
        i = 0
        while line and i < size:
            this_line = []
            j = 0
            temp = ""
            for char in line:
                if j < size:
                    if 49 <= ord(char) <= 57:
                        temp += char
                    elif char == '#':
                        variable = Variable(i, j)
                        this_line.append([variable])
                        self.variables.add(variable)
                        j += 1
                    elif temp != "":
                        variable = Variable(i, j, int(temp))
                        this_line.append([variable])
                        temp = ""
                        j += 1
                else:
                    break
            if j == size:
                i += 1
                self.board.append(this_line)
            line = file_object.readline()
        """ For wrong input raise Exception """
        if i != size:
            raise Exception
        """ To print the representation of the board on terminal """

        """ Assigning neighbors to a variable """
        self.neighbors = dict()
        for variable in self.variables:
            self.neighbors[variable] = set()
            var_i = variable.location[0]
            var_j = variable.location[1]
            for i in self.board:
                neighbor_var = i[var_j][0]
                if neighbor_var is not variable:
                    self.neighbors[variable].add(neighbor_var)
            for j in self.board[var_i]:
                neighbor_var = j[0]
                if neighbor_var is not variable:
                    self.neighbors[variable].add(neighbor_var)
            block_size = int(math.sqrt(size))
            block_i = (var_i // block_size) * block_size
            block_j = (var_j // block_size) * block_size
            for i in range(block_i, block_i + block_size):
                for j in range(block_j, block_j + block_size):
                    neighbor_var = self.board[i][j][0]
                    if neighbor_var is not variable:
                        self.neighbors[variable].add(neighbor_var)
        """ Defining domain of each variable """
        self.domains = dict()
        for variable in self.variables:
            self.domains[variable] = set(range(1, size + 1))

    def print(self):
        for i in self.board:
            line = ""
            for j in i:
                value = j[0].value
                if value is None:
                    line += "   |"
                else:
                    line += " " + str(value) + " |"
            print(line)

    def print_neighbor(self):
        """ Prints the neighbors of each variable after a colon(:) """
        for variable in self.variables:
            neighbor_line = str(variable.location) + ": "
            for neighbor in self.neighbors[variable]:
                neighbor_line += str(neighbor.location) + " "
            print(neighbor_line)
