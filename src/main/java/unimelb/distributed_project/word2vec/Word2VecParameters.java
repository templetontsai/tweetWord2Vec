/**
 * Distributed Project, TweetWord2Vec
 * Ting-Ying(Templeton) Tsai, Student ID: 723957
 */
package unimelb.distributed_project.word2vec;

/**
 * This class is used for setting the parameters for training word2vec. It follows the Builder
 * design pattern.
 *
 * @author Templeton Tsai
 */
public final class Word2VecParameters {


    private int windowSize = 5;
    private int minWordFeq = 5;
    private int numIteration = 5;
    private int layerSize = 100;
    private int seed = 40;
    private double learningRate = 0.025;

    /**
     * The constructor takes builder object to initialize all variables. All the variables are
     * required to fill in.
     *
     * @param builder Word2VecParametersBuilder object
     */
    private Word2VecParameters(Word2VecParametersBuilder builder) {

        this.windowSize = builder.windowSize;
        this.minWordFeq = builder.minWordFeq;
        this.numIteration = builder.numIteration;
        this.layerSize = builder.layerSize;
        this.learningRate = builder.learningRate;
        this.seed = builder.seed;

    }

    /**
     * Get the number of iteration
     *
     * @return int the number of iteration
     */
    public int getNumIteration() {
        return this.numIteration;
    }

    /**
     * Get the number of seed
     *
     * @return int the number of seed
     */
    public int getSeed() {
        return this.seed;
    }

    /**
     * Get the number of window size
     *
     * @return int the number of window size
     */
    public int getWindowSize() {
        return this.windowSize;
    }

    /**
     * Get the number of minimum word frequency for learning
     *
     * @return int the number of minimum word frequency
     */
    public int getMinWordFeq() {
        return this.minWordFeq;
    }

    /**
     * Get the number of layer size
     *
     * @return int the number of layer size
     */
    public int getLayerSize() {
        return this.layerSize;
    }

    /**
     * Get the number of learning rate
     *
     * @return int the number of learning rate
     */
    public double getLearningRate() {
        return this.learningRate;
    }

    /**
     * This inner class is used for setting the parameters for training word2vec. It follows the
     * Builder design pattern.
     *
     * @author Templeton Tsai
     */
    public static class Word2VecParametersBuilder {
        //Default
        private int windowSize = 5;
        private int minWordFeq = 5;
        private int numIteration = 5;
        private int layerSize = 100;
        private int seed = 40;
        private double learningRate = 0.025;

        /**
         * The constructor does nothing, all the parameters will be set through by the respective
         * functions against the parameter variables.
         */
        public Word2VecParametersBuilder() {

        }

        /**
         * @param windowSize String this is for setting the window size for word2vec training
         * @return Word2VecParametersBuilder builder design pattern
         */
        public Word2VecParametersBuilder windowSize(String windowSize) {
            if (!windowSize.equals("")) {
                this.windowSize = new Integer(windowSize);
            }

            return this;
        }

        /**
         * @param minWordFeq String this is for setting the minimum word frequency for word2vec
         *                   training
         * @return Word2VecParametersBuilder builder design pattern
         */
        public Word2VecParametersBuilder minWordFeq(String minWordFeq) {
            if (!minWordFeq.equals("")) {
                this.minWordFeq = new Integer(minWordFeq);
            }
            return this;
        }

        /**
         * @param numIteration String this is for setting the number of iteration
         *                     for word2vec training
         * @return Word2VecParametersBuilder builder design pattern
         */
        public Word2VecParametersBuilder numIteration(String numIteration) {
            if (!numIteration.equals("")) {
                this.numIteration = new Integer(numIteration);
            }
            return this;
        }

        /**
         * @param layerSize String this is for setting the number of layer size for word2vec
         *                  training
         * @return Word2VecParametersBuilder builder design pattern
         */
        public Word2VecParametersBuilder layerSize(String layerSize) {
            if (!layerSize.equals("")) {
                this.layerSize = new Integer(layerSize);
            }

            return this;
        }

        /**
         * @param seed String this is for setting the number of seed for word2vec training
         * @return Word2VecParametersBuilder builder design pattern
         */
        public Word2VecParametersBuilder seed(String seed) {
            if (!seed.equals("")) {
                this.seed = new Integer(seed);
            }
            return this;
        }

        /**
         * @param learningRate String this is for setting the number of learning rate for word2vec training
         * @return Word2VecParametersBuilder builder design pattern
         */
        public Word2VecParametersBuilder learningRate(String learningRate) {
            if (!learningRate.equals("")) {
                this.learningRate = new Double(learningRate);
            }
            return this;
        }


        /**
         * @return Word2VecParameters return the complete build object of Word2VecParameters
         */
        public Word2VecParameters build() {
            return new Word2VecParameters(this);
        }

    }
}
