import sys

def dance(string, commands):
    for command in commands:
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

            for x in range(len(string)):
                if string[x] == char1:
                    string[x] = char2
                elif string[x] == char2:
                    string[x] = char1

    return string

def main():
    N = 16
    string = [chr(ord('a') + x) for x in range(N)]
    string1 = [chr(ord('a') + x) for x in range(N)]
    commands = []
    memo = {}

    for line in sys.stdin:
        line = line.strip().split(',')
        for command in line:
            command = command.strip()
            commands.append(command)

    iterations = 1000000000
    current = 0
    while current < iterations:
        string = dance(string, commands)
        y = "".join(string)

        if y in memo:
            i = memo[y]
            cycles = int((iterations - current) / (current - i))
            current += (current - i)  * cycles

        memo[y] = current
        current += 1

    print("".join(string))

if __name__=="__main__":
    main()
