package irrigation.abox;


/*
 * @author Randy Massafra, Scott Streit
 * @version 0.1
 */
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

import irrigation.generated.Irrigation;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;

/**
 * LoadAwardsData
 * 
 * Based on NITRD provided NSF data in awards.xml
 */
public class LoadOrchard {

	final static Logger logger = Logger.getLogger(LoadOrchard.class.getName());

	/**
	 * Pull File(s) from provided path and pass on for processing
	 * 
	 * @param arg
	 *            [0] = input file/directory Path
	 * @param arg
	 *            [1] = output directory Path
	 */
	public static void main(String args[]) throws Exception {
		File file = new File(args[0]);
		String fileName = args[1];
		logger.info(" file is " + file.getName());
		logger.info(" fileName is " + fileName);

		String destDir = file.getAbsolutePath().endsWith(File.separator) ? file
				.getAbsolutePath() : file.getAbsolutePath() + File.separator;
		logger.info(" destDir = " + destDir);
		

		try
		{
		    makeOrachard(destDir, fileName);
		}

//		try {
//			doFile(destDir, fileName);
//		} 

		catch (Exception e) {
			logger.severe("error processing file " + file.getName()
					+ "absolute name " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	private static void makeOrachard(String destDir, String fileName) throws FileNotFoundException
    {
	    Model model = ModelFactory.createDefaultModel();
        ArrayList <AbstractOntClass> farmStuff = new ArrayList<AbstractOntClass>();
	    PatchFactory pFactory = new PatchFactory(model);
	    Patch p = null;
	    
	    String[] patchTypes = {"blueberry patch", "strawberry patch", "tomato patch"};
	    
	    //create a farm
        Individual farm = createFarm(model,"Morgantown Orchard", "Farmer John", "clay soil", patchTypes, "Farm");
	    
        // create a farmer
        Individual farmer = createIndividual(model, "Farmer John",
                "610-286-0123", "farmerjohn@morgantownorchard.com",
                "Morgantown Orchard", farm);

        // create a helper - should fail the rule is a Farmer
        Individual helper = createIndividual(model, "Helper Jane",
                "610-286-5432", "helperjane@morgantownorchard.com", 
                "Morgantown Orchard", null);
        
        Farmer f = new Farmer(model);
        f.setName("Farmer John");
        f.setEmail("farmerjohn@morgantownorchard.com");
        f.setPhone("610-286-0123");
        f.setOrginizationName("Morgantown Orchard");
        
        Farmland farmLand = new Farmland(model);
	    
	    p = pFactory.makePatch(SoilType.Clay, CropType.Apple);
	    farmStuff.add(p);
        farmLand.addPatch(p);
	    p = pFactory.makePatch(SoilType.Peat, CropType.Blueberry);
	    farmStuff.add(p);
        farmLand.addPatch(p);
	    p = pFactory.makePatch(SoilType.Clay, CropType.Corn);
	    farmStuff.add(p);
        farmLand.addPatch(p);
	    p = pFactory.makePatch(SoilType.Peat, CropType.Strawberry);
	    farmStuff.add(p);
        farmLand.addPatch(p);
	    p = pFactory.makePatch(SoilType.Peat, CropType.Tomato);
        farmStuff.add(p);
        farmLand.addPatch(p);
        
        for(AbstractOntClass thing : farmStuff)
        {
            model.add(thing.getStatements());
        }
        
        logger.fine("completed processing, outputting data");
        File outFile = new File(destDir + "orchard" + ".nt");
        PrintStream ps = new PrintStream(outFile);
        logger.info("completed processing, writing out N-TRIPLE");
        //model.write(ps, "N-TRIPLE");
        logger.info("process completed successfully");
    }

    /**
	 * 	  
	 * Strips data from the xml file and passes on for processing into a jena
	 * model.
	 * 
	 * @param fileName
	 *            - the input file.
	 * @param destDir
	 *            - the destination directory to place the Abox results.
	 */
	private static void doFile(String destDir, String fileName)
			throws FileNotFoundException {

		Model model = ModelFactory.createDefaultModel();
		insertWithProvenance(model);

//		 Output model
		logger.fine("completed processing, outputting data");
		File outFile = new File(destDir + "orchard" + ".nt");
		PrintStream ps = new PrintStream(outFile);
		logger.info("completed processing, writing out N-TRIPLE");
		model.write(ps, "N-TRIPLE");
		logger.info("process completed successfully");
	}

	/**
	 * Inserts provided data into the Jena model
	 * 
	 * @param model
	 *            - the input model.
	 * 
	 */
	public static void insertWithProvenance(Model model) {
		// create a farm
        String[] patchTypes = {"blueberry patch", "strawberry patch", "tomato patch"};
        Individual farm = createFarm(model,"Morgantown Orchard", "Farmer John", "clay soil", patchTypes, "Farm");
                
        // create a farmer
        Individual farmer = createIndividual(model, "Farmer John",
                "610-286-0123", "farmerjohn@morgantownorchard.com",
                "Morgantown Orchard", farm);

        // create a helper - should fail the rule is a Farmer
        Individual helper = createIndividual(model, "Helper Jane",
                "610-286-5432", "helperjane@morgantownorchard.com", 
                "Morgantown Orchard", null);
        
		
        // create a strawberry patch
        Individual strawberryPatch = createPatch(model, "Strawberry Patch", "104534", "106543");
        
        // create a blueberry patch
        Individual blueberryPatch = createPatch(model, "Blueberry Patch", "105344", "103423");
        
        // create a strawberry plant
        Individual strawberryPlant = createPlant(model, "Strawberry plant", ".02", ".08", ".00005");
        
        // create a blueberry plant
        Individual blueberryPlant = createPlant(model, "Blueberry plant", ".03", ".07", ".00005");
        
        // create soil for strawberry plant
        Individual strawberrySoil = createSoil(model, "Strawberry plant", "1.26", "3.00", "1.85", "1.26", strawberryPlant);
        
        // create soil for blueberry plant
        Individual blueberrySoil = createSoil(model, "Blueberry plant", "1.96", "3.00", "2.25", "1.96", blueberryPlant);
        
        // create a sensor for strawberry plant
        Individual strawberrySensor = createSensor(model, "Acclima Digital TDT RS500", "waterLevel", ".09", "Strawberry plant", strawberrySoil);
        
        // create a sensor for blueberry plant
        Individual blueberrySensor = createSensor(model, "Acclima Digital TDT RS500", "waterLevel", ".05", "Blueberry plant", blueberrySoil);
        
	}


	private static void printStatements(StmtIterator si) {
		while (si.hasNext()) {
			Statement s = si.next();
			logger.info("Statement is " + s);
		}
	}

	/**
	 * Create a basic individual using vcard
	 * 
	 * @param model
	 * @param name
	 * @param phone
	 * @param email
	 * @param role
	 * @return VCard Individual
	 */
    public static Individual createIndividual(Model model, String name,
            String phone, String email, String orgName, Individual farm) {
		if (name == null && phone == null && email == null)
			return null;
		
		Individual farmer = Irrigation.VCard.createIndividual(Irrigation.VCard.getURI()
				+ UUID.randomUUID());
		
        Statement s = null;
        
        s = model.createStatement(farmer, Irrigation.fn, name);
        model.add(s);
        s = model.createStatement(farmer, Irrigation.tel, phone);
        model.add(s);
        s = model.createStatement(farmer, Irrigation.email, email);
        model.add(s);
        s = model.createStatement(farmer, Irrigation.organization_name, orgName);
        model.add(s);
        if (farm != null) {
            s = model.createStatement(farmer, Irrigation.owns, farm);
            model.add(s);
        }
        
        return farmer;
	}


    public static Individual createFarm(Model model, String name, String owner,
            String soilType, String[] patchTypes, String type) {
		
		Individual farm = Irrigation.Farmland.createIndividual(Irrigation.Farmland.getURI()
				+ UUID.randomUUID());
        Statement s = null;
        s = model.createStatement(farm, Irrigation.name, name);
        model.add(s);
        s = model.createStatement(farm, Irrigation.owner, owner);
        model.add(s);
        s = model.createStatement(farm, Irrigation.soil, soilType);
        model.add(s);
        for (String patchType : patchTypes) {
            s = model.createStatement(farm, Irrigation.patch, patchType);
            model.add(s);
        }
        s = model.createStatement(farm, Irrigation.hasAType, type);
        model.add(s);
        
        return farm;
    }
	
	public static Individual createPatch(Model model, String name, String coordinateX, String coordinateY) {
		
		Individual patch = Irrigation.Patch.createIndividual(Irrigation.Patch.getURI() +
				UUID.randomUUID());
		Statement s = null;
		s = model.createStatement(patch, Irrigation.name, name);
		model.add(s);
		s = model.createStatement(patch, Irrigation.coordinateX, coordinateX);
		model.add(s);
		s = model.createStatement(patch, Irrigation.coordinateY, coordinateY);
		model.add(s);
		
		return patch;
	}
	
    public static Individual createPlant(Model model, String plantName, String lowerThreshold,
            String upperThreshold, String dailyGallonUsage) {
		
		Individual plant = Irrigation.Crop.createIndividual(Irrigation.Crop.getURI() +
				UUID.randomUUID());
        Statement s = null;
        s = model.createStatement(plant, Irrigation.plantName, plantName);
        model.add(s);
        s = model.createStatement(plant, Irrigation.lowerThreshold, lowerThreshold);
        model.add(s);
        s = model.createStatement(plant, Irrigation.upperThreshold, upperThreshold);
        model.add(s);
        s = model.createStatement(plant, Irrigation.dailyGallonUsage, dailyGallonUsage);
        model.add(s);
        
        return plant;
    }
	
    public static Individual createSensor(Model model, String name, String measures, String currentValue, 
            String plantType, Individual soil) {
		
		Individual sensor = Irrigation.Sensor.createIndividual(Irrigation.Sensor.getURI() +
				UUID.randomUUID());
		Statement s = null;
		s = model.createStatement(sensor, Irrigation.sensorName, name);
		model.add(s);
		s = model.createStatement(sensor, Irrigation.currentValue, currentValue);
		model.add(s);
		s = model.createStatement(sensor, Irrigation.measures, measures);
		model.add(s);
		s = model.createStatement(sensor, Irrigation.measuresPlantType, plantType);
		model.add(s);
		
		return sensor;
	}
	
    public static Individual createSoil(Model model, String plant, String saturationLevel, String maxSaturationLevel, String allowableDepletion,
            String availableWater, Individual crop) {
		
		Individual soil = Irrigation.Soil.createIndividual(Irrigation.Soil.getURI() +
				UUID.randomUUID());
        Statement s = null;
        s = model.createStatement(soil, Irrigation.plantName, plant);
        model.add(s);
        s = model.createStatement(soil, Irrigation.saturationLevel, saturationLevel);
        model.add(s);
        s = model.createStatement(soil, Irrigation.allowableDepletion, allowableDepletion);
        model.add(s);
        s = model.createStatement(soil, Irrigation.availableWater, availableWater);
        model.add(s);
        s = model.createStatement(soil, Irrigation.hasCrop, crop);
        model.add(s);
        
        return soil;
    }
	

}
