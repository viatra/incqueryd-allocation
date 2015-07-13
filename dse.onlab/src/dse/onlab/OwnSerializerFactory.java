package dse.onlab;

import onlab.PassTheRiver;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.viatra.dse.statecode.IStateCoder;
import org.eclipse.viatra.dse.statecode.IStateCoderFactory;

public class OwnSerializerFactory implements IStateCoderFactory{

	@Override
	public IStateCoder createStateCoder() {
		// TODO Auto-generated method stub
		return new Serializer();
	}

    

}