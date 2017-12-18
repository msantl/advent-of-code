import sys

def find_root(children):
    parents = children.keys()
    all_children = []

    for parent in parents:
        all_children += children[parent]

    for parent in parents:
        if parent not in all_children:
            return parent

    return None

def main():
    weights = {}
    children = {}
    for line in sys.stdin:
        line = line.strip().split("->")

        parent = line[0].split(" ")[0].strip()
        weight = int(line[0].split(" ")[1].strip()[1:-1])

        weights[parent] = weight
        children[parent] = []

        if len(line) == 2:
            for dep in line[1].split(","):
                child = dep.strip()
                children[parent].append(child)

    root = find_root(children)
    print(root)

if __name__=="__main__":
    main()
