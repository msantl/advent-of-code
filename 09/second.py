import sys

def solve(stream):
    ignore_next = False
    group_depth = 0
    in_garbage = False
    sol = 0
    for x in stream:
        if ignore_next:
            ignore_next = False
            continue
        ignore_next = False

        if in_garbage:
            if x == '>':
                in_garbage = False
            elif x == '!':
                ignore_next = True
            else:
                sol += 1
        else :
            if x == '{':
                group_depth += 1
            elif x == '}':
                group_depth -= 1
            elif x == '!':
                ignore_next = True
            elif x == '<':
                in_garbage = True

    return sol

def main():
    for line in sys.stdin:
        line = line.strip()
        print(solve(line))

if __name__=="__main__":
    main()
