import sys

disc_name = None
disc_offset = None

def dfs(curr, weights, children):
    global disc_name, disc_offset
    weight_cnt = {}
    weight_cnt = {}

    all_weight = 0
    for child in children[curr]:
        weight = dfs(child, weights, children)
        if weight in weight_cnt:
            weight_cnt[weight].append(child)
        else:
            weight_cnt[weight] = [child]

        all_weight += weight

    for weight in weight_cnt.keys():
        if disc_name is None and len(weight_cnt[weight]) == 1 and len(children[curr]) > 1:
            disc_name = weight_cnt[weight][0]
            disc_offset = weight - (all_weight - weight) / (len(children[curr]) - 1)

    return all_weight + weights[curr]

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

    if root is not None:
        dfs(root, weights, children)
        print(disc_name, weights[disc_name] - disc_offset)

if __name__=="__main__":
    main()
