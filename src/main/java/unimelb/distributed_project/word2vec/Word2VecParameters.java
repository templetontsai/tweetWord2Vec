package unimelb.distributed_project.word2vec;

/**
 * @author Templeton Tsai
 */
public final class Word2VecParameters {


    private int windowSize;
    private int minWordFeq;
    private int numIteration;
    private int layerSize;
    private int seed;
    private double learningRate;

    private Word2VecParameters(Word2VecParametersBuilder builder) {

        this.windowSize = builder.windowSize;
        this.minWordFeq = builder.minWordFeq;
        this.numIteration = builder.numIteration;
        this.layerSize = builder.layerSize;
        this.learningRate = builder.learningRate;
        this.seed = builder.seed;

    }

    public int getNumIteration() {
        return this.numIteration;
    }

    public int getSeed() {
        return this.seed;
    }

    public int getWindowSize() {
        return this.windowSize;
    }

    public int getMinWordFeq() {
        return this.minWordFeq;
    }

    public int getLayerSize() {
        return this.layerSize;
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public static class Word2VecParametersBuilder {
        //Default
        private int windowSize = 5;
        private int minWordFeq = 5;
        private int numIteration = 5;
        private int layerSize = 100;
        private int seed = 40;
        private double learningRate = 0.025;

        public Word2VecParametersBuilder() {

        }

        public Word2VecParametersBuilder windowSize(String windowSize) {
            if(!windowSize.equals("")) {
                this.windowSize = new Integer(windowSize);
            }

            return this;
        }

        public Word2VecParametersBuilder minWordFeq(String minWordFeq) {
            if(!minWordFeq.equals("")) {
                this.minWordFeq = new Integer(minWordFeq);
            }
            return this;
        }

        public Word2VecParametersBuilder numIteration(String numIteration) {
            if(!numIteration.equals("")) {
                this.numIteration = new Integer(numIteration);
            }
            return this;
        }

        public Word2VecParametersBuilder layerSize(String layerSize) {
            if(!layerSize.equals("")) {
                this.layerSize = new Integer(layerSize);
            }

            return this;
        }

        public Word2VecParametersBuilder seed(String seed) {
            if(!seed.equals("")) {
                this.seed = new Integer(seed);
            }
            return this;
        }

        public Word2VecParametersBuilder learningRate(String learningRate) {
            if(!learningRate.equals("")) {
                this.learningRate = new Double(learningRate);
            }
            return this;
        }


        public Word2VecParameters build() {
            return new Word2VecParameters(this);
        }

    }
}
