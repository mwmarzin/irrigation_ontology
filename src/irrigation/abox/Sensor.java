package irrigation.abox;

import irrigation.generated.Irrigation;

import com.hp.hpl.jena.rdf.model.Model;

public class Sensor extends AbstractOntClass
{

    public Sensor(Model model)
    {
        this.model = model;
        this.individual = Irrigation.Sensor.createIndividual(Irrigation.Sensor.getURI() + System.currentTimeMillis());
    }

}
