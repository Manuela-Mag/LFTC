from state import State


class Config:
    def __init__(self, start_sym):
        self.state = State.NORMAL
        # position of current symbol in input sequence
        self.index = 0
        # alpha, stores the way the parse is built
        self.work_stack = []
        # beta, part of the tree to be built
        self.input_stack = [start_sym]

