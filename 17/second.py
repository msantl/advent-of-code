import sys

def main():
    buff_size = 1
    null_pos = 0
    curr = 0
    sol = None
    step = int(sys.stdin.readline())

    for x in range(50000000):
        curr = ((curr + step) % buff_size) + 1

        if curr == null_pos:
            null_pos += 1
        elif curr == null_pos + 1:
            sol = x + 1

        buff_size += 1

    print(sol)

if __name__=="__main__":
    main()
