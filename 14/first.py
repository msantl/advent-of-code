import sys

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

def main():
    sol = 0
    for line in sys.stdin:
        line = line.strip()

        for x in range(128):
            knot = knot_hash(line + "-" + str(x))

            for y in knot:
                sol += bin(int(y, 16)).count('1')

    print(sol)


if __name__=="__main__":
    main()
