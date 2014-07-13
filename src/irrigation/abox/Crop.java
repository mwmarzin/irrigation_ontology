package irrigation.abox;

import com.hp.hpl.jena.rdf.model.Model;

import irrigation.generated.Irrigation;

public class Crop extends AbstractOntClass
{
    public static final String ROOT_DEPTH_KEY = "ROOT_DEPTH";
    public static final String EFFICIENCY_KEY = "EFFICIENCY";
    public static final String GALLONS_USED_KEY = "GALLONS_USED";
    public static final String DRY_WEIGHT_KEY = "DRY_WEIGHT";
    public static final String WILTING_PNT_KEY = "WILTING_PNT";
    
    private CropType cropType;
    private double rootDepth = 0;
    private double efficiency = 0;
    
    public Crop(Model model, CropType cropType)
    {
        this.model = model;
        this.individual = Irrigation.Crop.createIndividual(Irrigation.Crop.getURI() + System.currentTimeMillis());
        this.cropType = cropType;
        
        statements.put(GALLONS_USED_KEY, 
                model.createLiteralStatement(individual, 
                        Irrigation.dailyGallonUsage, 
                        Double.toString(cropType.dailyGallonsUsed)));
        statements.put(DRY_WEIGHT_KEY, 
                model.createLiteralStatement(individual, 
                        Irrigation.maxDryWeight, 
                        Double.toString(cropType.maxDryWeight)));
        statements.put(WILTING_PNT_KEY, 
                model.createLiteralStatement(individual, 
                        Irrigation.wiltingPoint, 
                        Double.toString(cropType.wiltingPoint)));
    }
    
    public CropType getCropType()
    {
        return cropType;
    }
    
    public double getRootDepth()
    {
        return rootDepth;
    }

    public void setRootDepth(double value)
    {
        removeExistingStatement(ROOT_DEPTH_KEY);
        rootDepth = value;
        statements.put(ROOT_DEPTH_KEY, 
                model.createStatement(individual, Irrigation.rootDepth, Double.toString(rootDepth)));
    }
    
    public double getEfficiency()
    {
        return efficiency;
    }

    public void setEfficiency(double value)
    {
        removeExistingStatement(EFFICIENCY_KEY);
        efficiency = value;
        statements.put(EFFICIENCY_KEY, 
                model.createStatement(individual, Irrigation.Efficiency, Double.toString(efficiency)));
    }
}
