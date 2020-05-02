"""
Tic Tac Toe Player
"""

import math

X = "X"
O = "O"
EMPTY = None


def initial_state():
    """
    Returns starting state of the board.
    """
    return [[EMPTY, EMPTY, EMPTY],
            [EMPTY, EMPTY, EMPTY],
            [EMPTY, EMPTY, EMPTY]]


def player(board):
    """
    Returns player who has the next turn on a board.
    """
    x_count = 0
    o_count = 0
    for i in range(0, len(board)) :
        for j in range(0, len(board[i])) :
            if board[i][j] == "X":
                x_count = x_count + 1
            elif board[i][j] == "O":
                o_count = o_count + 1

    if x_count > o_count :
        return "O"
    else :
        return "X"


def actions(board):
    """
    Returns set of all possible actions (i, j) available on the board.
    """
    out = []
    for i in range(0, len(board)):
        for j in range(0, len(board[i])):
            if board[i][j] == EMPTY:
                out.append([i,j])

    return out



def result(board, action):
    """
    Returns the board that results from making move (i, j) on the board.
    """
    if action[0] > 2 or action[0] < 0 or action[1] > 2 or action[1] < 0 :
        raise Exception

    players_turn = player(board)
    new_board = initial_state()
    for i in range (0,len(board)) :
        for j in range (0, len(board[i])) :
            new_board[i][j] = board[i][j]

    new_board[action[0]][action[1]] = players_turn
    return new_board


def winner(board):
    """
    Returns the winner of the game, if there is one.
    """

    # checking horizontally
    for i in range (0, 3) :
        if board[i][0] == board[i][1] and board[i][1] == board[i][2] :
            if board[i][0] is None :
                continue
            else :
                return board[i][0]

    #checking vertically
    for j in range (0, 3) :
        if board[0][j] == board[1][j] and board[1][j] == board[2][j] :
            if board[0][j] is None:
                continue
            else:
                return board[0][j]

    #checking first diagonal
    if board[0][0] == board[1][1] and board[1][1] == board[2][2] :
        if board[1][1] is None:
            pass
        else:
            return board[1][1]

    # checking second diagonal
    if board[2][0] == board[1][1] and board[1][1] == board[0][2] :
        if board[1][1] is None:
            pass
        else:
            return board[1][1]

    return None




def terminal(board):
    """
    Returns True if game is over, False otherwise.
    """
    if winner(board) is None:
        if not actions(board) :
            return True
        else :
            return False
    else :
        return True

def utility(board):
    """
    Returns 1 if X has won the game, -1 if O has won, 0 otherwise.
    """
    if winner(board) == "X" :
        return 1
    elif winner(board) == "O" :
        return -1
    else :
        return 0



def minimax(board):
    """
    Returns the optimal action for the current player on the board.
    """
    if terminal(board) :
        return None

    if player(board) == "X" :
        moves = actions(board)
        sync_max = [-2]
        out = []
        for i in range(0, len(moves)):
            temp = minimize(result(board, moves[i]), sync_max)
            if temp > sync_max[0]:
                sync_max[0] = temp
                out = moves[i]
        return out

    elif player(board) == "O" :
        moves = actions(board)
        sync_min = [2]
        out = []
        for i in range(0, len(moves)):
            temp = maximize(result(board, moves[i]), sync_min)
            if temp < sync_min[0]:
                sync_min[0] = temp
                out = moves[i]
        return out




def maximize(board, parent) :
    if terminal(board) :
        return utility(board)
    else:
        moves = actions(board)
        sync_max = [-2]
        for i in range (0, len(moves)) :
            temp = minimize(result(board, moves[i]), sync_max)
            if temp >= parent[0] :
                return 2
            elif temp > sync_max[0]:
                sync_max[0] = temp
        return sync_max[0]

def minimize (board, parent) :
    if terminal(board) :
        return utility(board)
    else :
        moves = actions(board)
        sync_min = [2]
        for i in range (0,len(moves)) :
            temp = maximize(result(board, moves[i]), sync_min)
            if temp <= parent[0] :
                return -2
            elif temp < sync_min[0]:
                sync_min[0] = temp
        return sync_min[0]
