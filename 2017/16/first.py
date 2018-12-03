import sys

def main():
    N = 16
    string = [chr(ord('a') + x) for x in range(N)]

    for line in sys.stdin:
        line = line.strip().split(',')
        for command in line:
            command = command.strip()

            if command[0] == 's':
                # spin
                size = int(command[1:])

                string = string[-size:] + string[:-size]

            elif command[0] == 'x':
                # swap by index
                id1 = int(command[1:].split('/')[0])
                id2 = int(command[1:].split('/')[1])

                temp = string[id1]
                string[id1] = string[id2]
                string[id2] = temp

            elif command[0] == 'p':
                # swap by char
                char1 = command[1:].split('/')[0]
                char2 = command[1:].split('/')[1]

                for x in range(N):
                    if string[x] == char1:
                        string[x] = char2
                    elif string[x] == char2:
                        string[x] = char1

    print("".join(string))


if __name__=="__main__":
    main()
