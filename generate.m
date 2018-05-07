%set (0, 'defaultaxesfontname', 'Helvetica')
%set (0, 'defaultaxesfontsize', 14)
%set (0, 'defaulttextfontname', 'Helvetica')
%set (0, 'defaulttextfontsize', 14) 

%% Load data
perfMatrix1 = csvread('latest.csv',1,0);
%perfMatrix2 = csvread('run1.csv',1,0);
%perfMatrix3 = csvread('run1.csv',1,0);
%perfMatrix4 = csvread('run1.csv',1,0);
%perfMatrix5 = csvread('run1.csv',1,0);
idVec = perfMatrix1(:,1);
qualityVec = perfMatrix1(:,2);
verdictMatrix = perfMatrix1(:,3:6);
bucketMatrix = perfMatrix1(:,7:18);
durationMatrix = perfMatrix1(:,19:24);
%durationMatrix = ( perfMatrix1(:,19:24) + perfMatrix2(:,19:24) + perfMatrix3(:,19:24) + perfMatrix4(:,19:24) + perfMatrix5(:,19:24) ) ./ 5;
graphMatrix = perfMatrix1(:,25:27);

%% Progression trace graph
verdictTrue = verdictMatrix(:,1);
verdictFalse = verdictMatrix(:,2);
verdictUnknown = verdictMatrix(:,3);
verdictNone =  verdictMatrix(:,4);

subplot(3,1,1);
%plot(idVec,verdictTrue,idVec,verdictFalse,'--',idVec,verdictUnknown,':',idVec,verdictNone,'-.',idVec,qualityVec,'black.');
plot(idVec,verdictTrue,idVec,verdictFalse,'--',idVec,verdictUnknown,':',idVec,qualityVec,'black.');
grid on;
axis([1 length(idVec) -0.1 1.1])
%xlabel('Iteration');
y = ylabel('Probability mass');
set(y, 'Units', 'Normalized', 'Position', [-0.06, 0.5, 0]);
legend('True','False','Unknown','Location','west');
legend('boxoff');


%% Time graph
durationTotalMs = durationMatrix(:,6)/1000000;

subplot(3,1,2);
plot(idVec,durationTotalMs);
grid on;
axis([1 length(idVec) 0.0 max(durationTotalMs)*1.1])
%xlabel('Iteration');
y = ylabel('Time (ms)');
set(y, 'Units', 'Normalized', 'Position', [-0.06, 0.5, 0]);


%% Space graph
componentVec = graphMatrix(:,1);
vertexVec = graphMatrix(:,2);
edgeVec = graphMatrix(:,3);

subplot(3,1,3);
plot(idVec,componentVec,idVec,vertexVec,'--',idVec,edgeVec,':');
grid on;
axis([1 length(idVec) 0 max(graphMatrix(:))*1.1])
xlabel('Iteration');
y = ylabel('Component count');
set(y, 'Units', 'Normalized', 'Position', [-0.06, 0.5, 0]);
legend('Components','Vertices','Edges','Location','northwest');
legend('boxoff');

%% Print figures
print('graph', '-dpng', '-r300');
