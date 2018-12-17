package elasticsearch.elasticsearch_sgbd.entity;

public class Metadata2 {
    public int took;
    public boolean timed_out;
    public class _shards{
        public short total;
        public short sucessful;
        public short skipped;
        public short failed;
    }
    public class hits{
        public int total;
        public float max_score;
        public Productes hits;
    }
}
