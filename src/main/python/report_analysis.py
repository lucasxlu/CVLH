import csv
import os
import json


def is_count_word(word, csv_file_path):
    """
    check whether a csv file contains the word
    :param word:
    :param csv_file_path:
    :return:
    """
    with open(csv_file_path, encoding='utf-8') as f:
        f_csv = csv.reader(f)
        for row in f_csv:
            if word in row[0].strip() and '' != row[0].strip():
                print(csv_file_path.split(os.path.sep)[len(csv_file_path.split(os.path.sep)) - 1].replace('.csv', '').replace('.csv', '') + ',' + row[0] + ',' + row[1])
                return True
            else:
                continue
        else:
            return False


def count_word(year_min, year_max, csv_dir):
    word_freq = {}
    for each_csv_file in os.listdir(csv_dir):
        if year_min <= int(each_csv_file.replace('.csv', '')) <= year_max:
            with open(csv_dir + os.path.sep + each_csv_file, encoding='utf-8') as f:
                f_csv = csv.reader(f)
                for row in f_csv:
                    if row[0] in word_freq.keys():
                        word_freq[row[0]] += float(row[1])
                    else:
                        word_freq[row[0]] = float(row[1])

    return word_freq


def dict2json(data_dict, json_filename):
    """
    convert python dict data structure into json file type
    :param data_dict:
    :return:
    """
    json_str = json.dumps(data_dict, ensure_ascii=False)
    with open('report_result/' + json_filename + '.json', mode='w', encoding='utf-8') as f:
        f.write(json_str)
        f.flush()
        f.close()


if __name__ == '__main__':
    csv_dir = 'C:/Users/29140/Desktop/GovReport/gov_hotwords'
    """
        word_freq = count_word(1954, 1976, csv_dir)
        print(word_freq)
        dict2json(word_freq, '毛泽东')
    """

    count_sum = 0
    for each_csv_file in os.listdir(csv_dir):
        if is_count_word('增长', csv_dir + os.path.sep + each_csv_file) is True:
            count_sum += 1

    print('It is proposed ' + str(count_sum) + ' times!')
