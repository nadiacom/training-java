package utils;

/**
 * Created by ebiz on 22/03/17.
 */
public class Pagination {

    public enum Limit {
        SHORT(10),
        MEDIUM(50),
        BIG(100);
        private int nb;

        /**
         * Create a Limit.
         *
         * @param nb int
         */
        Limit(int nb) {
            this.nb = nb;
        }

        /**
         * Return the limit nb for a Limit.
         *
         * @return int
         */
        public int getNb() {
            return nb;
        }

        /**
         * Return the limit nb from index. (0, 1, 2 => 10, 50, 100)
         *
         * @param choice int
         * @return int
         */
        public static int getLimitNb(int choice) {
            return values()[choice] != null ? values()[choice].getNb() : values()[0].getNb();
        }
    }
}
