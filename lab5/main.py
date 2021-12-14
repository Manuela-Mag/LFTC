from grammar import Grammar
from recursive_descendants import RecursiveDescendant
if __name__ == "__main__":
    gr = Grammar("g2.in")

    while True:
        print(
            "0.Exit\n1.Show all nonterminals\n2.Show all terminals\n3.Show productions\n4.Show productions of "
            "non-terminal\n5.Check\n6.Recursive Descendant")
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
            rec = RecursiveDescendant(gr)
            rec.recursive_descent(gr, ["declist"])



