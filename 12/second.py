import sys

memo = {}

def dfs(curr, V, g_id):
    global memo
    if curr in memo:
        return

    memo[curr] = g_id
    for x in V[curr]:
        dfs(x, V, g_id)

def main():
    global memo
    V = {}
    for line in sys.stdin:
        line = line.strip().split("<->")

        lhs = int(line[0].strip())
        V[lhs] = []

        for rhs in line[1].split(","):
            V[lhs].append(int(rhs))

    g_id = 0
    for x in V:
        if x in memo:
            continue

        dfs(x, V, g_id)
        g_id += 1

    print(g_id)

if __name__=="__main__":
    main()
