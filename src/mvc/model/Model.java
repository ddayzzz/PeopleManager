package mvc.model;

import common.ExceptionUtility;
import container.*;
import mvc.viewer.Viewer;

import java.util.ArrayList;

public interface Model {
    void process_insert();
    void process_list();
    void process_query();
    void  process_modify();
    void process_delete();
    void setViewer(Viewer viewer);
    Viewer getViewer();
    void save();
    Person getLastProcessedPerson();
}
