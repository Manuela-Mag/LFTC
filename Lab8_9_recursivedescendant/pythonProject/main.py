from grammar import Grammar
from recursive_descendants import RecursiveDescendant
NORMAL_STATE = "q"
BACK_STATE = "b"
FINAL_STATE = "f"  # successful - w part of L(G)
ERROR_STATE = "e"  # unsuccessful - w not part of L(G)


class Config:
    def __init__(self, startSymbol):
        self.s = NORMAL_STATE  # state of parsing
        self.i = 0  # position of current symbol in input sequence
        self.alpha = []  # working stack - stores the way the parse is build
        self.beta = [startSymbol]  # input stack - part of tree to be build

    def __str__(self):
        return "State: " + self.s + ", position of symbol in sequence: " + str(self.i) + ", working stack: " + str(
            self.alpha) + ", input stack " + str(self.beta)


def success(config):
    config.s = FINAL_STATE


# head of input stack is a nonTerminal
def expand(config, grammar):
    symbol = config.beta[0]
    y1 = grammar.P[symbol][0]
    config.alpha.append((symbol, y1))
    config.beta = y1 + config.beta[1:]


# head of input stack is a terminal = current symbol from input
def advance(config):
    config.i += 1
    config.alpha.append(config.beta[0])
    config.beta = config.beta[1:]


# head of working stack is a terminal
def back(config):
    config.i -= 1
    term = config.alpha.pop(-1)
    config.beta = [term] + config.beta


# head of working stack is nonTerminal
def anotherTry(config, grammar):
    lastProduction = config.alpha[-1]
    productions = grammar.P[lastProduction[0]]
    prods = []
    for p in productions:
        prods.append((lastProduction[0], p))
    nextProduction = getNext(lastProduction, prods)
    # normal state =>
    # (b,i,𝜶Aj,𝜸j𝜷) -> (q,i,𝜶Aj+1,𝜸j+1𝜷), if ∃ A→ 𝜸j+1
    if nextProduction:
        config.s = NORMAL_STATE
        config.alpha.pop(-1)
        config.alpha.append((nextProduction[0], nextProduction[1]))
        config.beta = config.beta[len(lastProduction[1]):]
        config.beta = nextProduction[1] + config.beta
    # (b,i,𝜶Aj,𝜸j𝜷) -> (e,i, 𝜶, 𝜷), if i=1, A =S, ERROR
    elif config.i == 0 and lastProduction[0] == grammar.S:
        config.s = ERROR_STATE
    # (b,i,𝜶Aj,𝜸j𝜷) -> (b,i, 𝜶, A 𝜷), otherwise with the exception
    else:
        config.alpha.pop(-1)
        config.beta = [lastProduction[0]] + config.beta[len(lastProduction[1]):]


def recursiveDescendent(grammar, sequence):
    config = Config(grammar.S)
    while config.s != FINAL_STATE and config.s != ERROR_STATE:
        if config.s == NORMAL_STATE:
            if config.i == len(sequence) and len(config.beta) == 0:
                success(config)
            elif len(config.beta) == 0:
                config.s = BACK_STATE
            else:
                if config.beta[0] in grammar.N:
                    expand(config, grammar)
                else:
                    if config.i == len(sequence):
                        config.s = BACK_STATE
                    elif config.beta[0] == sequence[config.i]:
                        advance(config)
                    else:
                        # momentary
                        config.s = BACK_STATE
        else:
            if config.s == BACK_STATE:
                if config.alpha[-1] in grammar.E:
                    back(config)
                else:
                    anotherTry(config, grammar)
    if config.s == ERROR_STATE:
        return "Syntax error", []
    else:
        prods = []
        for prod in config.alpha:
            if type(prod) is tuple:
                if prod[1] in grammar.P[prod[0]]:
                    prods.append(prod)
        print("Productions", prods)
    return "Sequence accepted!", prods


def getNext(lastProduction, productions):
    for i in range(len(productions)):
        if lastProduction == productions[i] and i < len(productions) - 1:
            return productions[i + 1]
    return None


def readSequence(lines):
    seq = []
    for line in lines:
        elms = line.split("'")
        seq.append(elms[1])
    print("Sequence", seq)
    return seq


if __name__ == "__main__":
    # gr = Grammar("g1.in")
    gr = Grammar("g2.in")

    while True:
        print(
            "0.Exit\n1.Show all nonterminals\n2.Show all terminals\n3.Show productions\n4.Show productions of non-terminal\n5.Check\n6.Parse")
        choice = int(input(">>"))
        if choice == 0:
            break
        elif choice == 1:
            print(gr.N)
        elif choice == 2:
            print(gr.E)
        elif choice == 3:
            print(gr.P)
        elif choice == 4:
            symbol = input(">>>")
            print(gr.show(symbol))
        elif choice == 5:
            print(gr.check())
        elif choice == 6:
            # with open("seq.txt") as file:
            #     lines = file.readlines()
            #     seq = readSequence(lines)
            # response, prods = recursiveDescendent(gr, seq)
            response, prods = recursiveDescendent(gr, ["var", "a", ";", "a", "=", "1", "+", "2", "+", "3", ";"])

            # response, prods = recursiveDescendent(gr, ["var", "a", ";", "a", "=", "1", "+", "2", "+", "3"])

            # r = RecursiveDescendant(gr)
            # response = r.recursive_descent(gr, ["var", "a", ";", "a", "=", "1", "+", "2", "+"])
            print(response)
            print("Productions", prods)
            # # f = open("out1.txt", "w")
            # f = open("out2.txt", "w")
            # # f = open("parsingTable.txt", "w")
            # f.close()