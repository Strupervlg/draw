package actions;

import shapes.Selection;

public abstract class SelectionAction {

    protected Selection selected;

    public SelectionAction(Selection selected) {
        this.selected = selected.clone();
    }
}
