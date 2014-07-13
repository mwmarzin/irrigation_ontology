package irrigation.abox;

import irrigation.generated.Irrigation;

import java.util.*;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.listeners.StatementListener;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

public class Soil extends AbstractOntClass
{
    public static final String TYPE_KEY = "SOIL_TYPE";
    
    protected ArrayList<Crop> crops = new ArrayList<Crop>();
    protected ArrayList<Sensor> sensors = new ArrayList<Sensor>();
    protected SoilType soilType;
    
    public Soil(Model model)
    {
        this.model = model;
        individual = Irrigation.Soil.createIndividual(Irrigation.Soil.getURI() + System.currentTimeMillis());
    }
    
    //TODO this could use more of the SoilType Enum
    public void addType(SoilType soilType)
    {
        OntClass ontSoilType ;
        removeExistingStatement(TYPE_KEY);
        this.soilType = soilType;
        switch(soilType)
        {
            case Clay:
                ontSoilType = Irrigation.Clay;
                break;
            case Peat:
                ontSoilType = Irrigation.Peat;
                break;
            default:
                ontSoilType = Irrigation.SoilType_TCM;
                break;
        }
        statements.put(TYPE_KEY, model.createStatement(individual, Irrigation.hasSoilType, ontSoilType ));
    }
    
    /** 
     * Add a Sensor to the sensors list
     * NOTE: We don't add a statement since the sensor will take care of 
     * its own statements. When getStatements is run, it will call each
     * sensor's getStatement 
     */
    public void addSensor(Sensor newSensor)
    {
        sensors.add(newSensor);
    }
    
    /** 
     * Add a Crop to the crops list
     * NOTE: We don't add a statement since the crop will take care of 
     * its own statements. When getStatements is run, it will call each
     * crop's getStatement 
     */
    public void addCrop(Crop crop)
    {
        crops.add(crop);
    }

    @Override
    public List<Statement> getStatements()
    {
        ArrayList <Statement> statementsList = new ArrayList<Statement>(statements.values());
        for(Crop crop:crops)
        {
            statementsList.add(model.createStatement(individual, Irrigation.hasCrop, crop.getIndividual()));
            statementsList.addAll(crop.getStatements());
        }
        
        for(Sensor sensor:sensors)
        {
            statementsList.add(model.createStatement(individual, Irrigation.hasSensor, sensor.getIndividual()));
            statementsList.addAll(sensor.getStatements());
        }
        
        return statementsList;
    }
}
