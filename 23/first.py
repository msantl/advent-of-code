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

    def get_value(x): return int(x) if is_number(x) else registers[x]

    for line in sys.stdin:
        line = line.strip().split()
        instructions.append(line)

    sol = 0
    ptr = 0
    while True:
        line = instructions[ptr]
        jmp = 1

        if line[0] == "set":
            registers[line[1]] = get_value(line[2])
        elif line[0] == "sub":
            registers[line[1]] -= get_value(line[2])
        elif line[0] == "add":
            registers[line[1]] += get_value(line[2])
        elif line[0] == "mul":
            registers[line[1]] *= get_value(line[2])
            sol += 1
        elif line[0] == "jnz":
            if get_value(line[1]) != 0:
                jmp = get_value(line[2])

        ptr += jmp

        if ptr < 0 or ptr >= len(instructions):
            break

    print(sol)

if __name__=="__main__":
    main()
