package irrigation.abox;

import irrigation.generated.Irrigation;

import java.util.*;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

public class Patch extends AbstractOntClass
{
    
    public static final String TYPE_KEY = "PATCH_TYPE"; 
    public static final String LOCATION_KEY = "LOCATION"; 
    public static final String Y_COORD_KEY = "Y_COORD"; 
    
    
    protected String type = "";
    protected Location location = null;
    protected ArrayList<Crop> crops = new ArrayList<Crop>();
    protected ArrayList<Sensor> sensors = new ArrayList<Sensor>();
    
    public Patch(Model model)
    {
        this.model = model;
        this.location = new Location(model);
        this.individual = Irrigation.Patch.createIndividual(Irrigation.Patch.getURI() + System.currentTimeMillis());
    }
    
    public void setType(String type)
    {
        removeExistingStatement(TYPE_KEY);
        
        this.type = type;
        statements.put(TYPE_KEY, model.createStatement(individual, Irrigation.name, type));
    }
    
    public void setLocation(Location location)
    {
        removeExistingStatement(LOCATION_KEY);
        
        this.location = location;
        statements.put(LOCATION_KEY, model.createStatement(individual, Irrigation.hasLocation, location.getIndividual()));
    }
    
    public static Individual createPatch(Model model, String name, String coordinateX, String coordinateY) {
        
        Individual patch = Irrigation.Patch.createIndividual(Irrigation.Patch.getURI() +
                System.currentTimeMillis());
        Statement s = null;
        s = model.createStatement(patch, Irrigation.name, name);
        model.add(s);
        s = model.createStatement(patch, Irrigation.coordinateX, coordinateX);
        model.add(s);
        s = model.createStatement(patch, Irrigation.coordinateY, coordinateY);
        model.add(s);
        
        return patch;
    }
    
    @Override
    public List<Statement> getStatements()
    {
        ArrayList <Statement> statementsList = new ArrayList<Statement>(statements.values());
        statementsList.addAll(location.getStatements());
        
        for(Crop crop:crops)
        {
            statementsList.addAll(crop.getStatements());
        }
        
        for(Sensor sensor:sensors)
        {
            statementsList.addAll(sensor.getStatements());
        }
        return statementsList;
    }

}
