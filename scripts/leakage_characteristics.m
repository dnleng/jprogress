set (0, 'defaultaxesfontname', 'Times')
set (0, 'defaultaxesfontsize', 12)
set (0, 'defaulttextfontname', 'Times')
set (0, 'defaulttextfontsize', 12) 

%% Load data
maxLength = 0;
for i = 5:190
    disp(int2str(i));
    perfMatrix1 = csvread(strcat('kr-online-1-',int2str(i),'-1.csv'),1,0);
    perfMatrix2 = csvread(strcat('kr-online-1-',int2str(i),'-2.csv'),1,0);
    perfMatrix3 = csvread(strcat('kr-online-1-',int2str(i),'-3.csv'),1,0);
    perfMatrix4 = csvread(strcat('kr-online-1-',int2str(i),'-4.csv'),1,0);
    perfMatrix5 = csvread(strcat('kr-online-1-',int2str(i),'-5.csv'),1,0);
    perfMatrix6 = csvread(strcat('kr-online-1-',int2str(i),'-6.csv'),1,0);
    perfMatrix7 = csvread(strcat('kr-online-1-',int2str(i),'-7.csv'),1,0);
    perfMatrix8 = csvread(strcat('kr-online-1-',int2str(i),'-8.csv'),1,0);
    perfMatrix9 = csvread(strcat('kr-online-1-',int2str(i),'-9.csv'),1,0);
    perfMatrix10 = csvread(strcat('kr-online-1-',int2str(i),'-10.csv'),1,0);
    
    n1 = size(perfMatrix1(:,20:25));
    n = n1(1);
    n2 = size(perfMatrix2(:,20:25));
    n = min(n, n2(1));
    n3 = size(perfMatrix3(:,20:25));
    n = min(n, n3(1));
    n4 = size(perfMatrix4(:,20:25));
    n = min(n, n4(1));
    n5 = size(perfMatrix5(:,20:25));
    n = min(n, n5(1));
    n6 = size(perfMatrix6(:,20:25));
    n = min(n, n6(1));
    n7 = size(perfMatrix7(:,20:25));
    n = min(n, n7(1));
    n8 = size(perfMatrix8(:,20:25));
    n = min(n, n8(1));
    n9 = size(perfMatrix9(:,20:25));
    n = min(n, n9(1));
    n10 = size(perfMatrix10(:,20:25));
    n = min(n, n10(1));
    durationMatrix = ( perfMatrix1(1:n,20:25) + perfMatrix2(1:n,20:25) + perfMatrix3(1:n,20:25) + perfMatrix4(1:n,20:25) + perfMatrix5(1:n,20:25) + perfMatrix6(1:n,20:25) + perfMatrix7(1:n,20:25) + perfMatrix8(1:n,20:25) + perfMatrix9(1:n,20:25) + perfMatrix10(1:n,20:25) ) ./ 10;
    durationTotalMs{i} = sum(durationMatrix(:,6))/1000000;
    sumMatrix = [ sum(perfMatrix1(1:n,25)) sum(perfMatrix2(1:n,25)) sum(perfMatrix3(1:n,25)) sum(perfMatrix4(1:n,25)) sum(perfMatrix5(1:n,25)) sum(perfMatrix6(1:n,25)) sum(perfMatrix7(1:n,25)) sum(perfMatrix8(1:n,25)) sum(perfMatrix9(1:n,25)) sum(perfMatrix10(1:n,25)) ];
    durationTotalSigma{i} = std(sumMatrix)/1000000;
    verdictTrue = perfMatrix1(:,3);
    verdictFalse = perfMatrix1(:,4);
    verdictUnknown = perfMatrix1(:,5);
    verdictNone = perfMatrix1(:,6);
    dataFalse{i} = verdictFalse;
    maxLength = max(maxLength, size(verdictFalse));
    dataUnknown{i} = verdictUnknown;
    maxLength = max(maxLength, size(verdictUnknown));
end

M = zeros(maxLength(1),186);
N = zeros(maxLength(1),186);
timeVec = zeros(1,186);
stdVec = zeros(1,186);
j = 1;
for i = 5:190
    disp(int2str(i));
    unknownSize = size(dataUnknown{i});
    falseSize = size(dataFalse{i});
    for l = 1:maxLength(1)
        if l <= unknownSize(1)
            M(l, j) = dataUnknown{i}(l);
        else
            M(l, j) = M(l-1, j);
        end
        if l <= falseSize(1)
            N(l, j) = dataFalse{i}(l);
        else
            N(l, j) = N(l-1, j);
        end
    end
    timeVec(1,j) = durationTotalMs{i}/1000;
    stdVec(1,j) = durationTotalSigma{i}/1000;
    j = j + 1;
end
idVec = 1:maxLength(1);

%% Generate figures
yyaxis left
index = 5:190;
plot(index,M(maxLength(1),:),'-.','linewidth',2);
grid on;
axis([5 190 0.0 1.0])
xlabel('MAX\_NODES');
y = ylabel('Leaked Probability at Termination');

yyaxis right
shade = [index, fliplr(index)];
inBetween = [timeVec + 2*stdVec, fliplr(timeVec - 2*stdVec)];
h = fill(shade, inBetween, [1,0.8,0.8]);
set(h,'edgecolor',[1,0.8,0.8]);
hold on;
plot(index,timeVec,'-','linewidth',2);
hold off;
grid on;
axis([5 190 0.0 max(timeVec + 2*stdVec)])
xlabel('MAX\_NODES');
y = ylabel('Time to Termination (sec)');


%% Print figures
print('leakage-characteristics', '-depsc');
