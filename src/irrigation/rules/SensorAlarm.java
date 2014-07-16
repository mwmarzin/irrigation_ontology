package irrigation.rules;

import irrigation.generated.Irrigation;

import java.util.logging.Logger;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.reasoner.rulesys.RuleContext;
import com.hp.hpl.jena.reasoner.rulesys.builtins.BaseBuiltin;
import com.hp.hpl.jena.util.iterator.ClosableIterator;

public class SensorAlarm extends BaseBuiltin
{

    final static Logger logger = Logger.getLogger(SensorAlarm.class
            .getName());
    public SensorAlarm()
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName()
    {
        return "SensorAlarm";
    }
    
    @Override
    public void headAction(Node[] args, int length, RuleContext context) 
    {
        double currentValue = 0;
        double lowerThreshold = 0;
        double upperThreshold = 0;
        
        Node sensor = getArg(0, args, context); // the sensor
        Node pred = getArg(1, args, context); // isAlarmedBy predicate
        Node crop = getArg(2, args, context); // the crop
        
        Node newPredicate = NodeFactory.createURI(Irrigation.isAlaramedBy.getURI());
        System.out.println("SensorAlarm in head action " + sensor + " " + pred + " " + crop);

        currentValue = getSubjectDouble(context, sensor, Irrigation.currentValue.asNode());
        lowerThreshold = getSubjectDouble(context, crop, Irrigation.lowerThreshold.asNode());
        upperThreshold = getSubjectDouble(context, crop, Irrigation.upperThreshold.asNode()); 
        
        if(currentValue < lowerThreshold)
        {
            System.out.println("HEY! I NEED WATER OVA HERE!!!");
            context.add(new Triple(sensor, newPredicate, crop));
        }
        else if (currentValue > upperThreshold)
        {
            System.out.println("STOP WATERING ME!!!");
            context.add(new Triple(sensor, newPredicate, crop));
        }
    }
    
    public double getSubjectDouble(RuleContext context, Node object, Node predicate)
    {
        double subjectValue = 0;
        
        ClosableIterator<Triple> ti =  context.find(object, predicate, (Node) null);
        while (ti.hasNext()) {
            Triple triple = ti.next();
            logger.info(" got a triple of " + triple);
            logger.info(" looking for subject ("
                     +  "\ntriple.getObject():" + triple.getObject() + 
                        "\ntriple.getPredicate()" + triple.getPredicate() + 
                        "\ntriple.getSubject()" + triple.getSubject());
            if(triple.getPredicate().matches(predicate))
            {
                subjectValue = new Double((String)triple.getObject().getLiteral().getValue());
                //break;
            }
        }
        
        return subjectValue;
    }
}
