package com.cvlh.spider;

import com.cvlh.entity.NlpJobType;
import com.cvlh.model.NlpJob;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * a web spider for http://www.nlpjob.com/jobs
 */
public class NlpJobSpider {
    private static final int TIME_OUT = 10000;

    public List<NlpJob> crawlJobInfo(String jobType) throws IOException {
        List<NlpJob> nlpJobList = new ArrayList<>();
        String requestUrl = String.format("http://www.nlpjob.com/jobs/%s?p=1", jobType);
        System.out.println(requestUrl);
        Pattern regex = Pattern.compile("http://www.nlpjob.com/job/\\w*}");
        Document document = Jsoup.parse(new URL(requestUrl), NlpJobSpider.TIME_OUT);
        document.getElementById("job-listings").getElementsByAttributeValueContaining("class", "row").forEach(element -> {
            String href = element.getElementsByTag("a").attr("href").toString();
            String jobName = element.getElementsByTag("a").text().toString();
            NlpJob nlpJob = new NlpJob(null, jobName, null, null, 0, href, null, jobType);
            try {
                int applyNum = Integer.parseInt(Jsoup.parse(new URL(href), NlpJobSpider.TIME_OUT).getElementById("applied-to-job").text().replace("申请人", "").trim());
                nlpJob.setApplynum(applyNum);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String company = element.getElementsByAttributeValue("class", "row-info").text().replace(jobName, "").split("in")[0].replace("at", "").trim();
            String location = element.getElementsByAttributeValue("class", "row-info").text().replace(jobName, "").split("in")[1].trim();
            nlpJob.setCompany(company);
            nlpJob.setLocation(location);

            String dateString = element.select("span.time-posted").text().trim();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
            if (dateString.length() > 0) {
                try {
                    nlpJob.setPublishdate(simpleDateFormat.parse(dateString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            nlpJobList.add(nlpJob);
            System.out.println(nlpJob);
        });

        return nlpJobList;
    }

    public static void main(String[] args) {
        try {
            new NlpJobSpider().crawlJobInfo(NlpJobType.SHIXI.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}