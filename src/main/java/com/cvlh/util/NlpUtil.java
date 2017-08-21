package com.cvlh.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NlpUtil {

    private static final String[] PUNCTUATION = new String[]{"，", "。", "！", "？", "\"", "~", "‘", "’", "、", ",", "."};

    public static List<Object> tokenize(String text, boolean withPosition) {
        for (String punctuation : PUNCTUATION)
            text = text.replace(punctuation, "");
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokenList = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
        List<Object> result = new ArrayList<>();
        for (SegToken segToken : segTokenList) {
            if (false == withPosition) {
                result.add(segToken.word);
            } else {
                result.add(segToken);
            }
        }

        return result;
    }

    /**
     * calculate term frequency
     *
     * @param words
     * @return
     * @version 1.0
     */
    public static Map<String, Double> tf(List<String> words) {
        Map<String, Double> hashMap = new HashMap<>();
        List<String> visitedWords = new ArrayList<>();
        for (String word : words) {
            if (visitedWords.contains(word)) {
                hashMap.put(word, hashMap.get(word) + 1 / words.size());
            } else {
                hashMap.put(word, (1.0D / words.size() + 0.0D));
            }
            visitedWords.add(word);
        }

        return hashMap;
    }

    /**
     * calculate inverse document frequency
     *
     * @param words
     * @return
     * @version 1.0
     */
    public static Map<String, Double> idf(List<String> words) {
        Map<String, Double> hashMap = new HashMap<>();
        List<String> visitedWords = new ArrayList<>();

        for (String word : words) {
            if (!visitedWords.contains(word)) {
                int count = 0;
                for (String w : words) {
                    if (w.equals(word))
                        count++;
                }
                hashMap.put(word, Math.log(words.size() / (1.0D + count)));
                visitedWords.add(word);
            }
        }

        return hashMap;
    }

    /**
     * calculate TF-IDF
     *
     * @param tf
     * @param idf
     * @return
     * @version 1.0
     */
    public static Map<String, Double> tfIdf(Map<String, Double> tf, Map<String, Double> idf) {
        Map<String, Double> hashMap = new HashMap<>();
        tf.forEach((s, aDouble) -> {
            hashMap.put(s, aDouble * idf.get(s));
        });

        return hashMap;
    }

    /**
     * train word2vec with a given document file
     *
     * @throws IOException
     * @version 1.0
     */
    public static void trainWordToVec(String docPath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        Files.readAllLines(Paths.get(docPath)).forEach(s -> {
            stringBuilder.append(s);
        });
        List<Object> list = NlpUtil.tokenize(stringBuilder.toString(), false);
        list.forEach(o -> {
            builder.append(o.toString()).append(" ");
        });

        Files.write(Paths.get("./corpus.txt"), builder.toString().getBytes());
        File file = new File("./corpus.txt");
        String filePath = file.getAbsolutePath();
        System.out.println("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator iter = new BasicLineIterator(filePath);
        TokenizerFactory t = new DefaultTokenizerFactory();

        t.setTokenPreProcessor(new CommonPreprocessor());
        System.out.println("Building model....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(2)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        System.out.println("Fitting Word2Vec model....");
        vec.fit();

        file.delete();
        // Write word vectors
        WordVectorSerializer.writeWord2VecModel(vec, Constant.WORD_2_VEC_PATH);
    }

    /**
     * calculate the cosine similarity between two given words
     * Note: You have to make sure you have trained word2vec model and serialize it before call this method
     *
     * @param word1
     * @param word2
     * @return
     * @version 1.0
     */
    public static double calcWordsSimilarity(String word1, String word2) {
        Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel(Constant.WORD_2_VEC_PATH);

        return word2Vec.similarity(word1, word2);
    }

    /**
     * get the <b>numOfWords</b> nearest words next to <b>word</b>
     * Note: You have to make sure you have trained word2vec model and serialize it before call this method
     *
     * @param word
     * @param numOfWords
     * @return
     * @version 1.0
     */
    public static Collection<String> findWordsNearest(String word, int numOfWords) {
        Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel(Constant.WORD_2_VEC_PATH);

        Collection<String> stringCollection = word2Vec.wordsNearest("人工智能", numOfWords);

        return stringCollection;
    }

}
