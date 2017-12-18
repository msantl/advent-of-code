import sys

def main():
    registers = {}
    for line in sys.stdin:
        line = line.strip().split()
        # 0 - reg, 1 - cmd, 2 - val1, 4 - reg1, 5 - op, 6 - val2
        if line[0] not in registers:
            registers[line[0]] = 0
        if line[4] not in registers:
            registers[line[4]] = 0

        result = False
        if line[5] == "<":
            result = registers[line[4]] < int(line[6])
        elif line[5] == ">":
            result = registers[line[4]] > int(line[6])
        elif line[5] == "==":
            result = registers[line[4]] == int(line[6])
        elif line[5] == ">=":
            result = registers[line[4]] >= int(line[6])
        elif line[5] == "<=":
            result = registers[line[4]] <= int(line[6])
        elif line[5] == "!=":
            result = registers[line[4]] != int(line[6])

        if result:
            if line[1] == "inc":
                registers[line[0]] += int(line[2])
            else:
                registers[line[0]] -= int(line[2])

    sol = None
    for reg in registers.keys():
        if sol is None or sol < registers[reg]:
            sol = registers[reg]

    print(sol)

if __name__=="__main__":
    main()
