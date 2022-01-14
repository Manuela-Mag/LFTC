from config import Config
from state import State


# head of input stack is [0]
# head of working stack is [len]


def getNextProduction(lastProduction, productions):
    for i in range(len(productions)):
        if lastProduction == productions[i] and i < len(productions) - 1:
            return productions[i + 1]
    return None


class RecursiveDescendant:
    def __init__(self, grammar):
        self.grammar = grammar

    @staticmethod
    def success(config):
        config.state = State.FINAL

    def expand(self, config):
        non_term = config.input_stack[0]
        first_prod = self.grammar.P[non_term][0]
        config.work_stack.append((non_term, first_prod))
        # config.input_stack = first_prod + config.input_stack[1:]
        new_stack_input = first_prod
        for i in range(len(config.input_stack)):
            new_stack_input.append(config.input_stack[i])
        config.input_stack = new_stack_input

    @staticmethod
    def back(config):
        config.index -= 1
        terminal = config.work_stack.pop(-1)
        config.input_stack = [terminal] + config.input_stack

    def anotherTry(self, config):
        # head of working stack is a non terminal
        # take another prod
        lastProduction = config.work_stack[-1]
        productions = self.grammar.P[lastProduction[0]]
        prods = []
        for p in productions:
            prods.append((lastProduction[0], p))
        nextProduction = getNextProduction(lastProduction, prods)
        # (b,i,𝜶Aj,𝜸j𝜷) -> (q,i,𝜶Aj+1,𝜸j+1𝜷), if ∃ A→ 𝜸j+1
        if nextProduction:
            config.state = State.NORMAL
            config.work_stack.pop(-1)
            config.work_stack.append((nextProduction[0], nextProduction[1]))
            config.input_stack = config.input_stack[len(lastProduction[1]):]
            # config.input_stack = nextProduction[1] + config.input_stack
            new_stack_input = [nextProduction[1]]
            for i in range(len(config.input_stack)):
                new_stack_input.append(config.input_stack[i])
                # config.input_stack.pop(i)
            config.input_stack = new_stack_input
        # (b,i,𝜶Aj,𝜸j𝜷) -> (e,i, 𝜶, 𝜷), if i=1, A =S, ERROR
        elif config.index == 0 and lastProduction[0] == self.grammar.S:
            config.state = State.ERROR
        # (b,i,𝜶Aj,𝜸j𝜷) -> (b,i, 𝜶, A 𝜷), otherwise with the exception
        else:
            config.work_stack.pop(-1)
            config.input_stack = [lastProduction[0]] + config.input_stack[len(lastProduction[1]):]

    @staticmethod
    def advance(config):
        config.index += 1
        config.work_stack.append(config.input_stack[0])
        config.input_stack = config.input_stack[1:]

    @staticmethod
    def momentaryInsuccess(config):
        config.state = State.BACK

    def recursive_descent(self, grammar, sequence):
        config = Config(grammar.S)
        while config.state != State.FINAL and config.state != State.ERROR:
            # print(config)
            if config.state == State.NORMAL:
                if config.index == len(sequence) and len(config.input_stack) == 0:
                    # SUCCESS
                    config.state = State.FINAL
                    # END SUCCESS
                elif len(config.input_stack) == 0:
                    config.state = State.BACK
                else:
                    if config.input_stack[0] in grammar.N:
                        # EXPAND
                        symbol = config.input_stack[0]
                        y1 = grammar.P[symbol][0]
                        config.work_stack.append((symbol, y1))
                        config.input_stack = y1 + config.input_stack[1:]
                        # new_stack_input = first_prod
                        # for i in range(len(config.input_stack)):
                        #     new_stack_input.append(config.input_stack[i])
                        # config.input_stack = new_stack_input
                        # END EXPAND
                    else:
                        if config.index == len(sequence):
                            config.state = State.BACK
                        elif config.work_stack[0] == sequence[config.index]:
                            # ADVANCE
                            config.index += 1
                            config.input_stack.append(config.work_stack[0])
                            config.work_stack = config.work_stack[1:]
                            # END ADVANCE
                        else:
                            config.state = State.BACK
            else:
                if config.state == State.BACK:
                    if config.input_stack[-1] in grammar.E:
                        # BACK
                        config.index -= 1
                        term = config.input_stack.pop(-1)
                        config.work_stack = [term] + config.work_stack
                        # END BACK
                    else:
                        # ANOTHER TRY
                        lastProduction = config.input_stack[-1]
                        productions = grammar.P[lastProduction[0]]
                        prods = []
                        for p in productions:
                            prods.append((lastProduction[0], p))
                        nextProduction = getNextProduction(lastProduction, prods)
                        if nextProduction:
                            config.s = State.NORMAL
                            config.input_stack.pop(-1)
                            config.input_stack.append((nextProduction[0], nextProduction[1]))
                            config.work_stack = config.work_stack[len(lastProduction[1]):]
                            config.work_stack = nextProduction[1] + config.work_stack
                        elif config.index == 0 and lastProduction[0] == grammar.S:
                            config.state = State.ERROR
                        else:
                            config.input_stack.pop(-1)
                            config.work_stack = [lastProduction[0]] + config.work_stack[len(lastProduction[1]):]
                        # END ANOTHER TRY
        if config.state == State.ERROR:
            print("Error was raised")
        else:
            print("Sequence accepted")
            print(self.buildProductionRules(config))

    def buildProductionRules(self, config):
        rules = []
        if config.state == State.ERROR:
            return False, []
        else:
            for prod in config.work_stack:
                if len(prod) > 1:
                    if (prod[0], prod[1]) in self.grammar.P:
                        rules.append(prod)
        return rules
