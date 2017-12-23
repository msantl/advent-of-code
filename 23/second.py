import sys

def is_number(x):
    try:
        x = int(x)
        return True
    except:
        return False

def main():
    played = None
    instructions = []
    registers = {}
    for x in range(8):
        registers[chr(x + ord('a'))] = 0

    registers['a'] = 1

    def get_value(x): return int(x) if is_number(x) else registers[x]

    for line in sys.stdin:
        line = line.strip().split()
        instructions.append(line)

    cnt = 0
    ptr = 0
    while True or cnt < 20:
        line = instructions[ptr]
        jmp = 1

        print(ptr, line, end='')
        for x in range(8):
            k = chr(x + ord('a'))
            print(" {}={},".format(k, registers[k]), end='')
        print()

        if line[0] == "set":
            registers[line[1]] = get_value(line[2])
        elif line[0] == "sub":
            registers[line[1]] -= get_value(line[2])
        elif line[0] == "mul":
            registers[line[1]] *= get_value(line[2])
        elif line[0] == "jnz":
            if get_value(line[1]) != 0:
                jmp = get_value(line[2])

        ptr += jmp
        cnt += 1

        if ptr < 0 or ptr >= len(instructions):
            break

    print(registers['h'])

if __name__=="__main__":
    main()
