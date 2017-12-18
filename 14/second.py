import sys

groups = {}
numbers = []
start = 0
offset = 0

def init():
    global numbers
    global start
    global offset

    numbers = [x for x in range(256)]
    start = 0
    offset = 0

def single_round(lengths):
    global numbers
    global start
    global offset
    for length in lengths:

        temp_list = []
        for i in range(length):
            temp_list.append(numbers[(i + start) % len(numbers)])

        temp_list.reverse()
        for i in range(length):
            numbers[(i + start) % len(numbers)] = temp_list[i]

        start += length + offset
        start %= len(numbers)
        offset += 1

    return numbers[0] * numbers[1]

def knot_hash(input_string):
    lengths = []
    static = [17, 31, 73, 47, 23]

    for x in input_string:
        lengths.append(ord(x))

    lengths += static

    init()
    for x in range(64):
        single_round(lengths)

    dense = []
    for x in range(16):
        xored = 0
        for y in range(16):
            xored ^= numbers[x * 16 + y]
        dense.append(xored)

    sol = ""
    for x in dense:
        sol += "%.2x" % x

    return sol

def get_binary_string(x, length = 4):
    s = "{0:b}".format(x)
    while len(s) < length:
        s = "0" + s
    return s

def bfs(x, y, grid):
    global groups
    dx = [0, 1, 0, -1]
    dy = [1, 0, -1, 0]

    queue = [(x,y)]

    while len(queue) > 0:
        (i, j) = queue.pop()

        for k in range(4):
            a = i + dx[k]
            b = j + dy[k]

            if a < 0 or a > 127 or b < 0 or b > 127:
                continue

            if grid[(a, b)] == grid[(x, y)] and groups[(a, b)] == 0:
                queue.append((a, b))
                groups[(a, b)] = groups[(x, y)]

def main():
    global groups
    grid = {}

    for line in sys.stdin:
        line = line.strip()

        for x in range(128):
            knot = knot_hash(line + "-" + str(x))

            j = 0
            for y in knot:
                s = get_binary_string(int(y, 16))
                for k in s:
                    grid[(x, j)] = k
                    groups[(x, j)] = 0
                    j += 1

    cnt = 1
    for x in range(128):
        for y in range(128):
            if grid[(x,y)] == "1" and groups[(x,y)] == 0:
                groups[(x, y)] = cnt
                bfs(x, y, grid)
                cnt += 1

    print(cnt - 1)


if __name__=="__main__":
    main()
