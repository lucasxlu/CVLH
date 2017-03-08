import requests
from bs4 import BeautifulSoup

import chardet
import os

headers = {
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    'Accept-Encoding': 'gzip, deflate, sdch'
}


def get_gov_report(year_url):
    response = requests.get(year_url, headers=headers)
    content = ''
    if response.status_code == 200:
        soup = BeautifulSoup(response.content.decode(chardet.detect(response.content)['encoding']).encode('UTF-8'),
                             "html5lib")
        for each_p in soup.find_all('p'):
            print(each_p.get_text())
            content += each_p.get_text()
            content += "\r\n"

    return content


def get_year_report_urls():
    urlmap = {}
    response = requests.get('http://www.gov.cn/guoqing/2006-02/16/content_2616810.htm', headers=headers)
    soup = BeautifulSoup(response.text, "html5lib")
    if response.status_code == 200:
        for each_td in soup.find_all('table')[0].find_all('td'):
            if each_td.a is not None and each_td.a.text.strip() is not "":
                urlmap[each_td.a.text] = each_td.a['href']

    return urlmap


def write_report(year, content):
    directory = '/home/xulu/code/gov_report'
    if not os.path.exists(directory) or not os.path.isdir(directory):
        os.makedirs(directory)

    with open(directory + '/' + str(year) + '.txt', mode='w', encoding='UTF-8') as f:
        f.write(content)
        f.flush()
        f.close()


if __name__ == '__main__':
    urlmap = get_year_report_urls()
    for each_text, each_url in urlmap.items():
        print(each_text + " : " + each_url)
        write_report(each_text, get_gov_report(each_url))