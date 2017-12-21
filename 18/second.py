import sys

def is_number(x):
    try:
        x = int(x)
        return True
    except:
        return False

def main():
    instructions = []
    for line in sys.stdin:
        line = line.strip().split()
        instructions.append(line)

    queue = {}
    registers = {}

    queue[0] = []
    queue[1] = []
    registers[0] = {}
    registers[1] = {}

    for x in range(26):
        registers[0][chr(x + ord('a'))] = 0
        registers[1][chr(x + ord('a'))] = 0

    registers[0]['p'] = 0
    registers[1]['p'] = 1

    def get_value(x, y): return int(x) if is_number(x) else registers[y][x]

    sol = 0

    ptr1 = 0
    ptr2 = 0
    deadlock1 = False
    deadlock2 = False

    while True:
        line1 = instructions[ptr1]
        line2 = instructions[ptr2]

        jmp1 = 1
        jmp2 = 1

        if line1[0] == "snd":
            snd = get_value(line1[1], 0)
            queue[1].append(snd)

        if line2[0] == "snd":
            snd = get_value(line2[1], 1)
            queue[0].append(snd)
            sol += 1

        if line1[0] == "set":
            registers[0][line1[1]] = get_value(line1[2], 0)

        if line2[0] == "set":
            registers[1][line2[1]] = get_value(line2[2], 1)

        if line1[0] == "add":
            registers[0][line1[1]] += get_value(line1[2], 0)

        if line2[0] == "add":
            registers[1][line2[1]] += get_value(line2[2], 1)

        if line1[0] == "mul":
            registers[0][line1[1]] *= get_value(line1[2], 0)

        if line2[0] == "mul":
            registers[1][line2[1]] *= get_value(line2[2], 1)

        if line1[0] == "mod":
            registers[0][line1[1]] %= get_value(line1[2], 0)

        if line2[0] == "mod":
            registers[1][line2[1]] %= get_value(line2[2], 1)

        if line1[0] == "rcv":
            if len(queue[0]):
                registers[0][line1[1]] = queue[0].pop(0)
                deadlock1 = False
            else:
                deadlock1 = True

        if line2[0] == "rcv":
            if len(queue[1]):
                registers[1][line2[1]] = queue[1].pop(0)
                deadlock2 = False
            else:
                deadlock2 = True

        if line1[0] == "jgz":
            if get_value(line1[1], 0) > 0:
                jmp1 = get_value(line1[2], 0)

        if line2[0] == "jgz":
            if get_value(line2[1], 1) > 0:
                jmp2 = get_value(line2[2], 1)

        if not deadlock1:
            ptr1 += jmp1

        if not deadlock2:
            ptr2 += jmp2

        if ptr1 < 0 or ptr1 >= len(instructions):
            break

        if ptr2 < 0 or ptr2 >= len(instructions):
            break

        if deadlock1 and deadlock2:
            break

    print(sol)

if __name__=="__main__":
    main()
