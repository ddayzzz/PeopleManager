package mvc.controler;

import common.PersonType;
import mvc.model.Model;
import mvc.model.StandardModel;

import java.util.HashMap;

public abstract class Controler {
    protected HashMap<PersonType, Model> stringModelHashMap = new HashMap<>();
    public Controler(){

    }

    public void setModel(PersonType type, Model model) {
        this.stringModelHashMap.put(type, model);
    }

    public final void saveAllModel(){
        for(Model model : stringModelHashMap.values()){
            model.save();
        }
    }
    public abstract void show();
}
