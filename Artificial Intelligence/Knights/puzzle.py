from logic import *

AKnight = Symbol("A is a Knight")
AKnave = Symbol("A is a Knave")

BKnight = Symbol("B is a Knight")
BKnave = Symbol("B is a Knave")

CKnight = Symbol("C is a Knight")
CKnave = Symbol("C is a Knave")

# Puzzle 0
# A says "I am both a knight and a knave."
knowledge0 = And(
    # either Knight or Knave
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),
    # A says I am both
    Biconditional(And(AKnight, AKnave), AKnight)
)

# Puzzle 1
# A says "We are both knaves."
# B says nothing.
knowledge1 = And(
    # either Knight or Knave
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),
    Or(BKnight, BKnave),
    Not(And(BKnight, BKnave)),
    # A says we both are Knaves
    Biconditional(And(AKnave, BKnave), AKnight),
)

# Puzzle 2
# A says "We are the same kind."
# B says "We are of different kinds."
knowledge2 = And(
    # either Knight or Knave
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),
    Or(BKnight, BKnave),
    Not(And(BKnight, BKnave)),
    # A says we both are same kind
    Biconditional(Or(And(AKnight, BKnight), And(AKnave, BKnave)), AKnight),
    # B says we are of different kinds
    Biconditional(Or(And(AKnight, BKnave), And(AKnave, BKnight)), BKnight)

)

# Puzzle 3
# A says either "I am a knight." or "I am a knave.", but you don't know which.
# B says "A said 'I am a knave'."
# B says "C is a knave."
# C says "A is a knight."
knowledge3 = And(
    # either Knight or Knave
    Or(AKnight, AKnave),
    Not(And(AKnight, AKnave)),
    Or(BKnight, BKnave),
    Not(And(BKnight, BKnave)),
    Or(CKnight, CKnave),
    Not(And(CKnight, CKnave)),
    # A says I am either Knight or Knave or both (Assuming that the OR used by A is inclusive OR, although it doesn't matter cuz # either Knight or Knave)
    Biconditional(Or(AKnave, AKnight), AKnight),
    # B says A said A is a Knave
    Biconditional(Biconditional(AKnight, AKnave), BKnight),
    # B says C is Knave
    Biconditional(CKnave, BKnight),
    # C says A is Knight
    Biconditional(AKnight, CKnight)
)


def main():
    symbols = [AKnight, AKnave, BKnight, BKnave, CKnight, CKnave]
    puzzles = [
        ("Puzzle 0", knowledge0),
        ("Puzzle 1", knowledge1),
        ("Puzzle 2", knowledge2),
        ("Puzzle 3", knowledge3)
    ]
    for puzzle, knowledge in puzzles:
        print(puzzle)
        if len(knowledge.conjuncts) == 0:
            print("    Not yet implemented.")
        else:
            for symbol in symbols:
                if model_check(knowledge, symbol):
                    print(f"    {symbol}")


if __name__ == "__main__":
    main()
