import sys

def main():
    start_state = "A"
    start_position = 0
    steps = 0

    parse_state = False
    curr_state = None

    curr_value = None

    write = 0
    move = 0
    next_state = None

    machine = {}
    track = {}

    for line in sys.stdin:
        line = line.strip().split()

        if len(line) == 0:
            continue
        elif line[0] == "Begin":
            start_state = line[3][:-1]
        elif line[0] == "Perform":
            steps = int(line[5])
        elif line[0] == "In":
            parse_state = True
            curr_state = line[2][:-1]
            machine[curr_state] = {}

        if parse_state:
            if line[0] == "If":
                curr_value = int(line[5][:-1])
            elif line[1] == "Write":
                write = int(line[4][:-1])
            elif line[1] == "Move":
                if line[6] == "left.":
                    move = -1
                else:
                    move = 1
            elif line[1] == "Continue":
                next_state = line[4][:-1]
                machine[curr_state][curr_value] = (write, move, next_state)

    for x in range(steps):
        v = 0
        if start_position in track:
            v = track[start_position]

        (w, m, n) = machine[start_state][v]

        track[start_position] = w
        start_position += m
        start_state = n

    sol = 0
    for k in track:
        if track[k] == 1:
            sol += 1

    print(sol)

if __name__ == "__main__":
    main()
