import sys
import copy

def get_empty_grid(size):
    small_grid = []
    for i in range(0, size):
        small_grid.append("." * size)
    return small_grid


def flip_h(grid):
    N = len(grid)
    flip = get_empty_grid(N)
    for x in range(N):
        line = ""
        for y in range(N):
            line += grid[x][N - y - 1]
        flip[x] = line
    return flip

def flip_v(grid):
    N = len(grid)
    flip = get_empty_grid(N)
    for x in range(N):
        line = ""
        for y in range(N):
            line += grid[N - x - 1][y]
        flip[x] = line
    return flip

def rotate(grid):
    N = len(grid)
    rot = get_empty_grid(N)
    for x in range(N):
        line = ""
        for y in range(N):
            line += grid[N - y - 1][x]
        rot[x] = line
    return rot

def main():
    grid = [".#.", "..#", "###"]
    rules = {}

    for x in range(2, 4):
        rules[x] = []

    for line in sys.stdin:
        line = line.strip().split(" => ")

        rule_lhs = line[0].split("/")
        rule_rhs = line[1].split("/")

        if len(rule_lhs) not in rules:
            rules[len(rule_lhs)] = []
        rules[len(rule_lhs)].append((rule_lhs, rule_rhs))

    for x in range(18):
        N = len(grid)

        size = 3
        if N % 2 == 0:
            size = 2

        new_grids = []
        k = 0

        for i in range(0, len(grid), size):
            new_grids.append([])
            for j in range(0, len(grid), size):

                small = get_empty_grid(size)
                for x in range(i, i + size):
                    small[x - i] = grid[x][j:j+size]

                new_grid = None
                for rule in rules[size]:
                    (lhs, rhs) = rule
                    if lhs == small:
                        new_grid = rhs
                        break
                    f1 = flip_v(small)
                    if lhs == f1:
                        new_grid = rhs
                        break
                    f2 = flip_h(small)
                    if lhs == f2:
                        new_grid = rhs
                        break
                    r1 = rotate(small)
                    if lhs == r1:
                        new_grid = rhs
                        break
                    r2 = rotate(r1)
                    if lhs == r2:
                        new_grid = rhs
                        break
                    r3 = rotate(r2)
                    if lhs == r3:
                        new_grid = rhs
                        break
                    rf11 = rotate(f1)
                    if lhs == rf11:
                        new_grid = rhs
                        break
                    rf12 = rotate(rf11)
                    if lhs == rf12:
                        new_grid = rhs
                        break
                    rf13 = rotate(rf12)
                    if lhs == rf13:
                        new_grid = rhs
                        break
                    rf21 = rotate(f2)
                    if lhs == rf21:
                        new_grid = rhs
                        break
                    rf22 = rotate(rf21)
                    if lhs == rf22:
                        new_grid = rhs
                        break
                    rf23 = rotate(rf22)
                    if lhs == rf23:
                        new_grid = rhs
                        break

                new_grids[k].append(new_grid)
            k += 1

        grid = []
        for i in range(len(new_grids)):
            for x in range(len(new_grids[i][0])):
                line = ""
                for j in range(len(new_grids[i])):
                    for y in range(len(new_grids[i][j][x])):
                        line += new_grids[i][j][x][y]
                grid.append(line)


    sol = 0
    for x in range(len(grid)):
        for y in range(len(grid[x])):
            if grid[x][y] == '#':
                sol += 1
    print(sol)

if __name__=="__main__":
    main()
