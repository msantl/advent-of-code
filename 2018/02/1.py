def count_letters(word):
    cnt = {}
    for i in range(26):
        cnt[chr(i + ord('a'))] = 0

    for l in word:
        cnt[l] += 1

    res = []
    for key in cnt.keys():
        res.append((key, cnt[key]))
    return res

def main():
    count_2 = 0
    count_3 = 0
    with open("1.in") as f:
        lines = f.readlines()

        for line in lines:
            letter_count = count_letters(line.strip())

            flag_2 = False
            flag_3 = False
            for l, c in letter_count:
                if c == 2 and not flag_2:
                    count_2 += 1
                    flag_2 = True
                if c == 3 and not flag_3:
                    count_3 += 1
                    flag_3 = True

    print(count_2 * count_3)

if __name__ == "__main__":
    main()
