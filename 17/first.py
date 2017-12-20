import sys

def main():
    buff = [0]
    curr = 0
    step = int(sys.stdin.readline())

    for x in range(2017):
        curr = ((curr + step) % len(buff)) + 1
        buff.insert(curr, x + 1)

    print(buff[curr + 1])

if __name__=="__main__":
    main()
