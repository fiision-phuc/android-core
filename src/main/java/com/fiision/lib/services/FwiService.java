package com.fiision.lib.services;


import java.security.*;


public abstract class FwiService {

	
	// Global static variables
	static private boolean _isInitialized = false;
	static private ThreadSafeClientConnManager _clientManager = null;
	
	
	// Global variables
	protected com.fiision.lib.request.FwiRequest _req = null;
	protected HttpClient   _con = null;
    protected HttpResponse _res = null;
	
    
	// Class's constructors
	public FwiService(com.fiision.lib.request.FwiRequest request) {
		if (!_isInitialized) {

            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                PlainSocketFactory plainSocket = PlainSocketFactory.getSocketFactory();

//                SSLSocketFactory sslSocket = SSLSocketFactory.getSocketFactory();
//                sslSocket.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http" , plainSocket, 80));
                schemeRegistry.register(new Scheme("https", sf  , 443));

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

                _clientManager = new ThreadSafeClientConnManager(params, schemeRegistry);
                _isInitialized = true;
            } catch (Exception e) {
//                return new DefaultHttpClient();
            }

		}
        _req = request;
		_con = new DefaultHttpClient(_clientManager, new BasicHttpParams());
	}

	
    // Class's properties
    public int status() {
        return (_res != null ? _res.getStatusLine().getStatusCode() : -1);
    }


    // Class's public abstract methods
    public abstract Object getResource();


	// Class's protected methods
	protected void execute() {
		/* Condition validation */
		if (this._req == null) return;
        
		try {
            _req.prepare();
			_res = _con.execute(_req);
		}
		catch (Exception ex) {
			_req.abort();
		}
	}
}
