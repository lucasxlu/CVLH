import jieba
import jieba.analyse
import csv
import os

"""
    This code is provided by @LucasX for research only
"""


def get_hot_words(article):
    # jieba.analyse.set_stop_words("/home/xulu/code/gov_rep_code/stopwords.txt")
    # jieba.load_userdict("/home/xulu/code/gov_rep_code/userdict.txt")
    return jieba.analyse.extract_tags(article, topK=50, withWeight=True, allowPOS=())


def get_article_content(article_path):
    with open(article_path, mode='rt', encoding='utf-8') as f:
        return "".join(f.readlines())


def out_tfidf_csv(filename_prefix, list):
    hot_words_dir = '/home/xulu/code/gov_hotwords'
    if not os.path.exists(hot_words_dir) or not os.path.isdir(hot_words_dir):
        os.makedirs(hot_words_dir)

    with open(hot_words_dir + os.path.sep + filename_prefix + '.csv', 'w', newline='') as f:
        f_csv = csv.writer(f)
        f_csv.writerows(list)


if __name__ == '__main__':
    file_dir = '/home/xulu/code/gov_report'
    for each_file in os.listdir(file_dir):
        article = get_article_content(file_dir + os.path.sep + each_file)
        words_list = get_hot_words(article)
        out_tfidf_csv(each_file.replace('.txt', ''), words_list)
