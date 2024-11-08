package actions;

public interface MergeAction {
    public boolean merge(MergeAction other);

    public void setNonMerged();

    public boolean canMerge();
}
