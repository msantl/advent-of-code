import sys

def main():
    numbers = [x for x in range(256)]
    start = 0
    offset = 0
    for line in sys.stdin:
        line = line.strip().split(",")
        for length in line:
            length = int(length)

            temp_list = []
            for i in range(length):
                temp_list.append(numbers[(i + start) % len(numbers)])

            temp_list.reverse()
            for i in range(length):
                numbers[(i + start) % len(numbers)] = temp_list[i]

            start += length + offset
            start %= len(numbers)
            offset += 1

    print(numbers[0] * numbers[1])


if __name__=="__main__":
    main()
