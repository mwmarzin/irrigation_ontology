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
    public static final String SOIL_KEY = "SOIL"; 
    
    
    protected String type = "";
    protected Location location = null;
    protected Soil soil = null;
    
    public Patch(Model model)
    {
        this.model = model;
        this.location = new Location(model);
        this.individual = super.createIndividual(Irrigation.Patch);
    }
    
    public void setType(String type)
    {
        removeExistingStatement(TYPE_KEY);
        
        this.type = type;
        statements.put(TYPE_KEY, model.createLiteralStatement(individual, Irrigation.name, type));
    }
    
    public void setLocation(Location location)
    {
        removeExistingStatement(LOCATION_KEY);
        
        this.location = location;
        statements.put(LOCATION_KEY, model.createStatement(individual, Irrigation.hasLocation, location.getIndividual()));
    }
    
    public void setSoil(Soil soil)
    {
        removeExistingStatement(SOIL_KEY);
        this.soil = soil;
        statements.put(SOIL_KEY, model.createStatement(individual, Irrigation.hasSoil, soil.getIndividual()));
    }
    
    @Override
    public ArrayList<Statement> getStatements()
    {
        ArrayList <Statement> statementsList = super.getStatements();
        statementsList.addAll(location.getStatements());
        statementsList.addAll(soil.getStatements());
        
        return statementsList;
    }

}
