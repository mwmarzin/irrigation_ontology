package irrigation.abox;

import java.util.UUID;

import irrigation.generated.Irrigation;

import com.hp.hpl.jena.rdf.model.Model;

public class Sensor extends AbstractOntClass
{
    public static final String SOIL_KEY = "SOIL_KEY";

    
    Model model = null;
    Soil soil = null;
    double currentValue = 0;
    
    
    public Sensor(Model model)
    {
        this.model = model;
        this.individual = Irrigation.Sensor.createIndividual(Irrigation.Sensor.getURI() + UUID.randomUUID());
    }
    
    public void setSoil(Soil soil)
    {
        removeExistingStatement(SOIL_KEY);
        this.soil = soil;
        
        statements.put(SOIL_KEY, model.createStatement(individual, 
                Irrigation.hasSoil, 
                soil.getIndividual()));
    }

}
