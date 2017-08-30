package com.cvlh.service.impl;

import com.cvlh.service.NlpService;
import com.cvlh.util.MathUtil;
import com.cvlh.util.NlpUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
public class NlpServiceImpl implements NlpService {
    @Override
    public double calcDocsSimilarity(String doc1, String doc2) {

        int featureDimension = 100;
        ArrayList<String> stringArrayListDoc1 = new ArrayList<>();
        ArrayList<String> stringArrayListDoc2 = new ArrayList<>();

        NlpUtil.tokenize(doc1, false).forEach(o -> {
            stringArrayListDoc1.add(o.toString());
        });

        NlpUtil.tokenize(doc2, false).forEach(o -> {
            stringArrayListDoc2.add(o.toString());
        });

        Map<String, Double> map1 = NlpUtil.tfIdf(NlpUtil.tf(stringArrayListDoc1), NlpUtil.idf(stringArrayListDoc1));
        Map<String, Double> map2 = NlpUtil.tfIdf(NlpUtil.tf(stringArrayListDoc2), NlpUtil.idf(stringArrayListDoc2));

        Collection<Double> collection1 = map1.values();
        Collection<Double> collection2 = map2.values();

        double[] tfIdf1 = new double[featureDimension];
        double[] tfIdf2 = new double[featureDimension];

        if (collection1.size() > featureDimension) {
            for (int i = 0; i < featureDimension; i++) {
                tfIdf1[i] = (double) collection1.toArray()[i];
            }
        } else {
            for (int i = 0; i < collection1.size(); i++) {
                tfIdf1[i] = (double) collection1.toArray()[i];
            }
            for (int i = collection1.size(); i < featureDimension; i++) {
                tfIdf1[i] = 0;
            }
        }

        if (collection2.size() > 100) {
            for (int i = 0; i < 100; i++) {
                tfIdf2[i] = (double) collection2.toArray()[i];
            }
        } else {
            for (int i = 0; i < collection2.size(); i++) {
                tfIdf2[i] = (double) collection2.toArray()[i];
            }
            for (int i = collection2.size(); i < featureDimension; i++) {
                tfIdf2[i] = 0;
            }
        }

        double similarity = MathUtil.cosineSimilarity(tfIdf1, tfIdf2);

        return similarity;
    }
}
