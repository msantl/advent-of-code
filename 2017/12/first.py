import sys

memo = {}

def dfs(curr, V):
    global memo
    if curr in memo:
        return

    memo[curr] = 1
    for x in V[curr]:
        dfs(x, V)

def main():
    V = {}
    for line in sys.stdin:
        line = line.strip().split("<->")

        lhs = int(line[0].strip())
        V[lhs] = []

        for rhs in line[1].split(","):
            V[lhs].append(int(rhs))

    dfs(0, V)

    print(len(memo.keys()))

if __name__=="__main__":
    main()

